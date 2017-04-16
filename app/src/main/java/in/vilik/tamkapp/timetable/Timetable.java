package in.vilik.tamkapp.timetable;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.utils.API;
import in.vilik.tamkapp.utils.DataLoader;
import in.vilik.tamkapp.utils.OnDataLoadedListener;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by vili on 16/04/2017.
 */

public class Timetable extends DataLoader implements API {
    private List<TimetableElement> elements;

    private Request request;

    private Context context;

    private OnTimetableUpdatedListener listener;

    public Timetable(Context context) {
        super(context);

        setApi(this);
        RequestBody body = generateRequestBody(new Date(), null, "15TIKOOT");
        setRequestBody(body);

        this.context = context;
        this.elements = new ArrayList<>();

        setOnDataLoadedListener(new OnDataLoadedListener() {
            @Override
            public void onSuccess(String data) {
                parseTimetable(data);
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

    public List<TimetableElement> getElements() {
        return elements;
    }

    public void setElements(List<TimetableElement> elements) {
        this.elements = elements;
    }

    private void parseTimetable(String data) {
        System.out.println(data);

        triggerOnTimetableUpdatedListener(true);
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

                    System.out.println(json.toString());

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
