package in.vilik.tamkapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * Handles caching of data for offline usage.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0425
 * @since 1.7
 */
public class DataCache {

    /**
     * Prefix for cache preferences.
     *
     * Cache preference key consists of prefix and cache key.
     * Value contains information on when the cache was last updated.
     */
    private final static String CACHE_PREFERENCE_PREFIX = "cached_";

    /**
     * Prefix for cache files.
     */
    private final static String CACHE_FILE_PREFIX = "";

    /**
     * Suffix for cache files.
     */
    private final static String CACHE_FILE_SUFFIX = ".json";

    /**
     * Determines if there is data available in cache with specified cache key.
     *
     * @param context   Context for cache
     * @param cacheKey  Cache key
     * @param maxAge    Max age for cached data in milliseconds
     * @return          If there is up-to-date data available in cache
     */
    public static boolean has(Context context, String cacheKey, long maxAge) {
        Date maxAgeDate = new Date(new Date().getTime() - maxAge);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        long lastCached = prefs.getLong(CACHE_PREFERENCE_PREFIX + cacheKey, -1);

        return lastCached != -1 && new Date(lastCached).after(maxAgeDate);
    }

    /**
     * Writes data to the cache.
     *
     * @param context   Context for cache
     * @param cacheKey  Cache key
     * @param data      Data to cache
     */
    public static void write(Context context, String cacheKey, String data) {
        String filename = CACHE_FILE_PREFIX + cacheKey + CACHE_FILE_SUFFIX;
        DataStorage.write(context, filename, data);

        Date now = new Date();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putLong(CACHE_PREFERENCE_PREFIX + cacheKey, now.getTime()).apply();
    }

    /**
     * Reads data from the cache.
     *
     * @param context   Context for cache
     * @param cacheKey  Cache key
     * @return          Data from cache, null if not available.
     */
    public static String read(Context context, String cacheKey) {
        String filename = CACHE_FILE_PREFIX + cacheKey + CACHE_FILE_SUFFIX;

        return DataStorage.read(context, filename);
    }
}
