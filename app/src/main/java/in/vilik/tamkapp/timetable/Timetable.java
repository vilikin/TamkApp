package in.vilik.tamkapp.timetable;

import android.content.Context;

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

    private Request request;

    private Context context;

    private OnTimetableUpdatedListener listener;

    private String studentGroup;

    public Timetable(Context context) {
        super(context);

        this.days = new ArrayList<>();
        this.studentGroup = "15tikoot";

        setApi(this);
        RequestBody body = generateRequestBody(new Date(), null, studentGroup);
        setRequestBody(body);

        this.context = context;

        setOnDataLoadedListener(new OnDataLoadedListener() {
            @Override
            public void onSuccess(String data) {
                boolean successfullyParsed = parseTimetable(data);
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

    public List<TimetableElement> getElements(int maxDays) {
        List<TimetableElement> elements = new ArrayList<>();
        Reservation reservationForNowBlock = null;

        Date now = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 5);

        Date todayMorning = calendar.getTime();

        int dayCounter = 0;

        for (Day day : days) {
            if (day.getDate().after(todayMorning)) {
                elements.add(day);
                dayCounter++;

                for (Reservation reservation : day.getReservations()) {
                    elements.add(reservation);

                    if (DateUtil.isOnRange(reservation.getStartDate(),
                            reservation.getEndDate(), now) &&
                            reservationForNowBlock == null) {
                        reservationForNowBlock = reservation;
                    }
                }

                if (dayCounter >= maxDays) {
                    break;
                }
            }
        }

        if (reservationForNowBlock != null) {
            elements.add(0, new NowBlock(reservationForNowBlock));
        }

        return elements;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<TimetableElement> elements = getElements(7);

        for (TimetableElement element : elements) {
            sb.append(element.getType().name());
            sb.append("\n");
            switch (element.getType()) {
                case DAY_HEADER:
                    Day d = (Day)element;
                    sb.append(d.getDate());
                    sb.append("\n");
                    break;
                case RESERVATION:
                    Reservation r = (Reservation)element;
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
            addReservationsToDays(reservations);

            return true;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addReservationsToDays(List<Reservation> reservations) {
        days.clear();

        for (final Reservation reservation : reservations) {
            boolean matchingDayFound = false;

            for (Day day : days) {
                if (DateUtil.areOnSameDay(day.getDate(), reservation.getStartDate())) {
                    day.getReservations().add(reservation);
                    matchingDayFound = true;
                }
            }

            if (!matchingDayFound) {
                Day day = new Day(this);

                day.setDate(reservation.getStartDate());
                day.getReservations().add(reservation);

                days.add(day);
            }
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
        return "timetable_" + studentGroup;
    }

    private RequestBody generateRequestBody(final Date startDate,
                                            final Date endDate,
                                            final String studentGroup) {
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

                    json.put("startDate", df.format(startDate));

                    if (endDate != null) {
                        json.put("endDate", df.format(endDate));
                    }

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
