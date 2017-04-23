package in.vilik.tamkapp.timetable;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.R;
import in.vilik.tamkapp.SettingsActivity;
import in.vilik.tamkapp.timetable.notes.Note;
import in.vilik.tamkapp.timetable.notes.NoteStorage;
import in.vilik.tamkapp.utils.API;
import in.vilik.tamkapp.utils.AppPreferences;
import in.vilik.tamkapp.utils.DataLoader;
import in.vilik.tamkapp.utils.DateUtil;
import in.vilik.tamkapp.utils.OnDataLoadedListener;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by vili on 16/04/2017.
 */

public class Timetable extends DataLoader implements API {
    private List<Day> days;

    private List<TimetableElement> elements;

    private Request request;

    private Context context;

    private AppPreferences preferences;

    private OnTimetableUpdatedListener listener;

    private String language;

    public Timetable(Context context, String language) {
        super(context);

        setLanguage(language);

        this.context = context;
        preferences = new AppPreferences(context);

        this.days = new ArrayList<>();
        this.elements = new ArrayList<>();

        setApi(this);
        RequestBody body = generateRequestBody();
        setRequestBody(body);

        setOnDataLoadedListener(new OnDataLoadedListener() {
            @Override
            public void onSuccess(String data) {
                boolean successfullyParsed = parseTimetable(data);
                refreshVisibleElementsList(false);
                triggerOnTimetableUpdatedListener(successfullyParsed);
            }

            @Override
            public void onFailure() {
                generateDays();
                refreshVisibleElementsList(true);
                triggerOnTimetableUpdatedListener(false);
            }
        });
    }

    private void setLanguage(String language) {
        if (!language.equals("fi")) {
            this.language = "en";
        } else {
            this.language = language;
        }
    }

    private void triggerOnTimetableUpdatedListener(boolean success) {
        if (success && listener != null) {
            listener.onSuccess();
        } else if (listener != null) {
            listener.onError();
        }
    }

    public void setOnTimetableUpdatedListener(OnTimetableUpdatedListener listener) {
        this.listener = listener;
    }

    public void removeOnTimetableUpdatedListener() {
        this.listener = null;
    }

    public Context getContext() {
        return context;
    }

    private void refreshVisibleElementsList(boolean loadError) {
        elements.clear();

        if (loadError) {
            AnnouncementBlock ab = new AnnouncementBlock();
            Resources resources = context.getResources();

            ab.setTitle(resources.getString(R.string.timetable_block_load_error_title));
            ab.setBody(resources.getString(R.string.timetable_block_load_error_body));

            elements.add(ab);
        } else if (preferences.getTimetableStudentGroup().isEmpty()) {
            AnnouncementBlock ab = new AnnouncementBlock();
            Resources resources = context.getResources();

            ab.setTitle(resources.getString(R.string.timetable_block_no_group_title));
            ab.setBody(resources.getString(R.string.timetable_block_no_group_body));

            ab.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SettingsActivity.class);
                    context.getApplicationContext().startActivity(intent);
                }
            });

            elements.add(ab);
        }

        Reservation reservationForNowBlock = null;

        Date now = new Date();

        for (Day day : days) {
            if (day.getReservations().size() > 0) {
                elements.add(day);

                for (Reservation reservation : day.getReservations()) {
                    elements.add(reservation);

                    if (DateUtil.isOnRange(reservation.getStartDate(),
                            reservation.getEndDate(), now) &&
                            reservationForNowBlock == null) {
                        reservationForNowBlock = reservation;
                    }
                }
            } else {
                elements.add(new EmptyDay(day));
            }

            for (Note note : day.getNotes()) {
                elements.add(note);
            }
        }

        if (reservationForNowBlock != null) {
            elements.add(0, new NowBlock(reservationForNowBlock));
        }
    }

    public List<TimetableElement> getElements() {
        return elements;
    }

    private void addReservationsToDays(List<Reservation> reservations) {
        for (final Reservation reservation : reservations) {
            for (Day day : days) {
                if (DateUtil.areOnSameDay(day.getDate(), reservation.getStartDate())) {
                    reservation.setParent(day);
                    day.getReservations().add(reservation);
                }
            }
        }

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();

        if (!days.isEmpty() && calendar.get(Calendar.HOUR_OF_DAY) >= 18) {
            List<Reservation> firstDayReservations = days.get(0).getReservations();

            if (!firstDayReservations.isEmpty()) {
                Reservation last = firstDayReservations
                        .get(firstDayReservations.size() - 1);

                if (now.after(last.getEndDate()) && days.get(0).getNotes().isEmpty()) {
                    days.remove(0);
                }
            } else if (days.get(0).getNotes().isEmpty()) {
                days.remove(0);
            }
        }
    }

    private void generateDays() {
        days.clear();

        String period = preferences.getTimetablePeriod();

        boolean showWeekends = preferences.areWeekendsShown();

        List<Date> dates;

        switch (period) {
            case "this_week":
                dates = DateUtil.getThisWeek();
                break;
            case "one_month":
                dates = DateUtil.getDays(30);
                break;
            case "half_year":
                dates = DateUtil.getDays(182);
                break;
            default:
                dates = DateUtil.getDays(0);
        }

        List<Note> notes = NoteStorage.getNotes(context);

        Calendar calendar = Calendar.getInstance();

        for (Date date : dates) {
            boolean insertDay = true;

            if (!showWeekends) {
                calendar.setTime(date);
                int weekDay = calendar.get(Calendar.DAY_OF_WEEK);

                if (weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY) {
                    insertDay = false;
                }
            }

            if (insertDay) {
                Day day = new Day(this);
                day.setDate(date);

                for (Note note : notes) {
                    if (DateUtil.areOnSameDay(date, note.getDate())) {
                        note.setTimetable(this);
                        day.getNotes().add(note);
                    }
                }

                days.add(day);
            }
        }
    }

    /*  ----------------------- PARSING ------------------------------ */

    private boolean parseTimetable(String data) {
        try {
            JSONObject json = new JSONObject(data);

            JSONArray reservationsJson = json.getJSONArray("reservations");

            List<Reservation> reservations = parseReservations(reservationsJson);

            generateDays();
            addReservationsToDays(reservations);

            return true;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<Reservation> parseReservations(JSONArray reservationsJson) throws JSONException, ParseException {
        List<Reservation> reservations = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        for (int i = 0; i < reservationsJson.length(); i++) {
            Reservation reservation = new Reservation();
            JSONObject reservationJson = reservationsJson.getJSONObject(i);

            reservation.setId(reservationJson.getInt("id"));
            reservation.setSubject(reservationJson.getString("subject"));
            reservation.setDescription(reservationJson.getString("description"));

            Date startDate = df.parse(reservationJson.getString("startDate"));
            reservation.setStartDate(startDate);
            Date endDate = df.parse(reservationJson.getString("endDate"));
            reservation.setEndDate(endDate);

            ClassRoom classRoom = null;
            List<Realization> realizations = new ArrayList<>();
            List<String> studentGroups = new ArrayList<>();

            JSONArray resources = reservationJson.getJSONArray("resources");

            for (int r = 0; r < resources.length(); r++) {
                JSONObject resource = resources.getJSONObject(r);

                String type = resource.getString("type");

                switch (type) {
                    case "room":
                        classRoom = parseClassRoom(resource);
                        break;
                    case "realization":
                        realizations.add(parseRealization(resource));
                        break;
                    case "student_group":
                        studentGroups.add(resource.getString("code"));
                        break;
                }
            }

            reservation.setClassRoom(classRoom);
            reservation.setRealizations(realizations);
            reservation.setStudentGroups(studentGroups);

            reservations.add(reservation);
        }

        return reservations;
    }

    private ClassRoom parseClassRoom(JSONObject resource) throws JSONException {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName(resource.getString("name"));
        classRoom.setCode(resource.getString("code"));

        JSONObject building = resource.getJSONObject("parent");

        classRoom.setBuilding(building.getString("code"));

        return classRoom;
    }

    private Realization parseRealization(JSONObject resource) throws JSONException {
        Realization realization = new Realization();
        realization.setName(resource.getString("name"));
        realization.setCode(resource.getString("code"));

        return realization;
    }

    /*  ----------------------- API ------------------------------ */

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    public Type getType() {
        return Type.TIMETABLE;
    }

    @Override
    public String getCacheKey() {
        return "timetable_" + preferences.getTimetableStudentGroup() + "_" + language;
    }

    private RequestBody generateRequestBody() {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse("application/json");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                try {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                    JSONObject json = new JSONObject();

                    Date now = new Date();

                    json.put("startDate", df.format(new Date(now.getTime() - 1000 * 60 * 60 * 24)));

                    String studentGroup = preferences.getTimetableStudentGroup();

                    JSONArray studentGroups = new JSONArray();
                    studentGroups.put(studentGroup);

                    json.put("studentGroup", studentGroups);

                    Debug.log("generateRequestBody()", "Generated: " + json.toString());

                    sink.writeUtf8(json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void setRequestBody(RequestBody body) {
        request = new Request.Builder()
                .post(body)
                .url("https://opendata.tamk.fi/r1/reservation/search/")
                .addHeader("Authorization", "Basic dWdXbUNVa21BOHoweFVIYVR0U0g6")
                .addHeader("Accept-Language", language)
                .build();
    }
}
