package in.vilik.tamkapp.timetable.deadlines;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.utils.DataStorage;

/**
 * Created by vili on 22/04/2017.
 */

public class DeadlineStorage {
    private static List<Deadline> cache = null;
    private static final String DEADLINE_FILENAME = "deadlines.json";

    public static List<Deadline> getDeadlines(Context context) {
        if (cache == null) {
            ArrayList<Deadline> deadlines = new ArrayList<>();
            String rawData = DataStorage.read(context, DEADLINE_FILENAME);

            if (rawData != null) {
                try {
                    JSONArray json = new JSONArray(rawData);


                    for (int i = 0; i < json.length(); i++) {
                        JSONObject deadlineJson = json.getJSONObject(i);

                        Deadline deadline = new Deadline();

                        deadline.setName(deadlineJson.getString("name"));
                        deadline.setDate(new Date(deadlineJson.getLong("date")));
                        deadline.setFullDay(deadlineJson.getBoolean("fullDay"));

                        deadlines.add(deadline);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            cache = deadlines;
        }

        return new ArrayList<>(cache);
    }

    public static boolean addDeadline(Context context, Deadline deadline) {
        List<Deadline> deadlines = getDeadlines(context);

        deadlines.add(deadline);

        try {
            String json = deadlinesToJson(deadlines);

            DataStorage.write(context, DEADLINE_FILENAME, json);

            cache = deadlines;
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean removeDeadline(Context context, Deadline deadline) {
        List<Deadline> deadlines = getDeadlines(context);

        if (deadlines.remove(deadline)) {
            try {
                String json = deadlinesToJson(deadlines);

                DataStorage.write(context, DEADLINE_FILENAME, json);

                cache = deadlines;
                return true;
            } catch (JSONException e) {
                return false;
            }
        }

        return false;
    }


    private static String deadlinesToJson(List<Deadline> deadlines) throws JSONException {
        JSONArray array = new JSONArray();

        for (Deadline deadline : deadlines) {
            JSONObject deadlineObject = new JSONObject();

            deadlineObject.put("name", deadline.getName());
            deadlineObject.put("date", deadline.getDate().getTime());
            deadlineObject.put("fullDay", deadline.isFullDay());

            array.put(deadlineObject);
        }

        return array.toString();
    }
}
