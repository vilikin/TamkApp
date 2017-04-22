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
    private final static String CACHE_PREFERENCE_PREFIX = "cached_";
    private final static String CACHE_FILE_PREFIX = "";
    private final static String CACHE_FILE_SUFFIX = ".json";

    public static boolean has(Context context, String cacheKey, long maxAge) {
        Date maxAgeDate = new Date(new Date().getTime() - maxAge);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        long lastCached = prefs.getLong(CACHE_PREFERENCE_PREFIX + cacheKey, -1);

        return lastCached != -1 && new Date(lastCached).after(maxAgeDate);
    }

    public static void write(Context context, String cacheKey, String data) {
        String filename = CACHE_FILE_PREFIX + cacheKey + CACHE_FILE_SUFFIX;
        DataWriter.write(context, filename, data);

        Date now = new Date();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putLong(CACHE_PREFERENCE_PREFIX + cacheKey, now.getTime()).apply();
    }

    public static String read(Context context, String cacheKey) {
        String filename = CACHE_FILE_PREFIX + cacheKey + CACHE_FILE_SUFFIX;

        return DataWriter.read(context, filename);
    }
}
