package in.vilik.tamkapp.timetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
import in.vilik.tamkapp.utils.API;
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

    private SharedPreferences preferences;

    private OnTimetableUpdatedListener listener;

    private String studentGroupPreferenceKey;
    private String studentGroupDefault;

    public Timetable(Context context) {
        super(context);

        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        studentGroupPreferenceKey = context.getResources()
                .getString(R.string.preference_key_student_group);
        studentGroupDefault = context.getResources()
                .getString(R.string.group_default);

        this.days = new ArrayList<>();
        this.elements = new ArrayList<>();

        setApi(this);
        RequestBody body = generateRequestBody();
        setRequestBody(body);

        setOnDataLoadedListener(new OnDataLoadedListener() {
            @Override
            public void onSuccess(String data) {
                boolean successfullyParsed = parseTimetable(data);
                refreshVisibleElementsList();
                triggerOnTimetableUpdatedListener(successfullyParsed);
            }

            @Override
            public void onFailure() {
                triggerOnTimetableUpdatedListener(false);
            }
        });
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

    private void refreshVisibleElementsList() {
        elements.clear();
        Reservation reservationForNowBlock = null;

        Date now = new Date();

        for (Day day : days) {
            elements.add(day);

            for (Reservation reservation : day.getReservations()) {
                elements.add(reservation);

                if (DateUtil.isOnRange(reservation.getStartDate(),
                        reservation.getEndDate(), now) &&
                        reservationForNowBlock == null) {
                    reservationForNowBlock = reservation;
                }
            }
        }

        if (reservationForNowBlock != null) {
            elements.add(0, new NowBlock(reservationForNowBlock));
        }
    }

    public List<TimetableElement> getElements() {
        return elements;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<TimetableElement> elements = getElements();

        for (TimetableElement element : elements) {
            sb.append(element.getType().name());
            sb.append("\n");
            switch (element.getType()) {
                case DAY_HEADER:
                    Day d = (Day) element;
                    sb.append(d.getDate());
                    sb.append("\n");
                    break;
                case RESERVATION:
                    Reservation r = (Reservation) element;
                    sb.append(r.toString());
                    sb.append("\n");
                    break;
            }
        }

        return sb.toString();
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

    private void addReservationsToDays(List<Reservation> reservations) {
        for (final Reservation reservation : reservations) {
            for (Day day : days) {
                if (DateUtil.areOnSameDay(day.getDate(), reservation.getStartDate())) {
                    day.getReservations().add(reservation);
                }
            }
        }
    }

    private void generateDays() {
        days.clear();

        String preferenceKeyPeriod = context
                .getResources()
                .getString(R.string.preference_key_timetable_period);

        String preferenceKeyWeekends = context
                .getResources()
                .getString(R.string.preference_key_timetable_show_weekends);

        String defaultPreference = context
                .getResources()
                .getString(R.string.timetable_period_default);

        String period = preferences
                .getString(preferenceKeyPeriod, defaultPreference);

        boolean showWeekends = preferences
                .getBoolean(preferenceKeyWeekends, true);

        Debug.log("generateDays()", "Generating days: " + period +
                " | Show weekends: " + showWeekends);

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

        Debug.log("generateDays()", "Got " + dates.size() + " dates in total");

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
                days.add(day);
            }
        }

        Debug.log("generateDays()", "There are now " + days.size() + " days in timetable");
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
        String studentGroup = preferences
                .getString(studentGroupPreferenceKey, studentGroupDefault);

        return "timetable_" + studentGroup;
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

                    json.put("startDate", df.format(new Date(now.getTime() - 1000 * 60 * 60 * 12)));

                    String studentGroup = preferences
                            .getString(studentGroupPreferenceKey, studentGroupDefault);

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
                .build();
    }
}
