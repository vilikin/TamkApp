package in.vilik.tamkapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import in.vilik.tamkapp.Debug;

/**
 * Created by vili on 13/04/2017.
 */
public class DataCache {
    final static String CACHE_PREFERENCE_PREFIX = "cached_";
    final static String CACHE_FILE_PREFIX = "";
    final static String CACHE_FILE_SUFFIX = ".json";

    public static boolean has(Context context, API.Type apiType, long maxAge) {
        Date maxAgeDate = new Date(new Date().getTime() - maxAge);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        long lastCached = prefs.getLong(CACHE_PREFERENCE_PREFIX + apiType.name(), -1);

        return lastCached != -1 && new Date(lastCached).after(maxAgeDate);
    }

    public static void write(Context context, API.Type apiType, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context
                    .openFileOutput(CACHE_FILE_PREFIX + apiType.name() + CACHE_FILE_SUFFIX,
                            Context.MODE_PRIVATE));

            outputStreamWriter.write(data);
            outputStreamWriter.close();

            Date now = new Date();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            prefs.edit().putLong(CACHE_PREFERENCE_PREFIX + apiType.name(), now.getTime()).apply();
        }

        catch (IOException e) {
            Debug.log("writeToFile()", "File write failed: " + e.toString());
        }
    }

    public static String read(Context context, API.Type apiType) {
        String content = null;

        try {
            InputStream inputStream = context
                    .openFileInput(CACHE_FILE_PREFIX + apiType.name() + CACHE_FILE_SUFFIX);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                content = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Debug.log("readFromFile()", "File not found: " + e.toString());
        } catch (IOException e) {
            Debug.log("readFromFile()", "Can not read file: " + e.toString());
        }

        return content;
    }
}
