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
 * Implements persisting of users notes.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class NoteStorage {

    /**
     * Variable that contains "cache" of user notes.
     *
     * This is retrieved first time from file and after that
     * all modifications go to the real file through this variable.
     */
    private static List<Note> cache = null;

    /**
     * Name of the file used for storing users notes.
     */
    private static final String NOTE_FILENAME = "notes.json";

    /**
     * Gets list of users notes, preferably from static cache variable.
     *
     * If cache is not available, parses JSON from the specified file
     * and saves it in cache variable.
     *
     * @param context   Context
     * @return          List of users notes
     */
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

    /**
     * Inserts new note to the list and persists modified list to a file.
     *
     * @param context   Context
     * @param note      Note to add to the list
     * @return          If the operation was successful or not
     */
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

    /**
     * Removes note from the list and persists modified list to a file.
     *
     * @param context   Context
     * @param note      Note to remove from the list
     * @return          If the operation was successful or not
     */
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

    /**
     * Serializes list of notes into a JSON string.
     *
     * @param notes             List of notes to serialize
     * @return                  Notes in a JSON format
     * @throws JSONException    An exception indicating that serialization failed
     */
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
