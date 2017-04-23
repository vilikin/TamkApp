package in.vilik.tamkapp.timetable.notes;

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

public class NoteStorage {
    private static List<Note> cache = null;
    private static final String NOTE_FILENAME = "notes.json";

    public static List<Note> getNotes(Context context) {
        if (cache == null) {
            ArrayList<Note> notes = new ArrayList<>();
            String rawData = DataStorage.read(context, NOTE_FILENAME);

            if (rawData != null) {
                try {
                    JSONArray json = new JSONArray(rawData);


                    for (int i = 0; i < json.length(); i++) {
                        JSONObject noteJson = json.getJSONObject(i);

                        Note note = new Note();

                        note.setName(noteJson.getString("name"));
                        note.setDate(new Date(noteJson.getLong("date")));
                        note.setFullDay(noteJson.getBoolean("fullDay"));
                        note.setNoteType(Note.NoteType.valueOf(noteJson.getString("type")));

                        notes.add(note);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            cache = notes;
        }

        return new ArrayList<>(cache);
    }

    public static boolean addNote(Context context, Note note) {
        List<Note> notes = getNotes(context);

        notes.add(note);

        try {
            String json = notesToJson(notes);

            DataStorage.write(context, NOTE_FILENAME, json);

            cache = notes;
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean removeNote(Context context, Note note) {
        List<Note> notes = getNotes(context);

        if (notes.remove(note)) {
            try {
                String json = notesToJson(notes);

                DataStorage.write(context, NOTE_FILENAME, json);

                cache = notes;
                return true;
            } catch (JSONException e) {
                return false;
            }
        }

        return false;
    }


    private static String notesToJson(List<Note> notes) throws JSONException {
        JSONArray array = new JSONArray();

        for (Note note : notes) {
            JSONObject noteObject = new JSONObject();

            noteObject.put("name", note.getName());
            noteObject.put("date", note.getDate().getTime());
            noteObject.put("fullDay", note.isFullDay());
            noteObject.put("type", note.getNoteType().name());

            array.put(noteObject);
        }

        return array.toString();
    }
}
