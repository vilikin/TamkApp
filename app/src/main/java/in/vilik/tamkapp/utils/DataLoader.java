package in.vilik.tamkapp.utils;


import android.content.Context;

import java.io.IOException;

import in.vilik.tamkapp.Debug;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Implements a base class for all API handlers.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public abstract class DataLoader {

    /**
     * Defines all available data loading strategies that can be used.
     *
     * CACHE_FIRST: Tries to read cache first, if unavailable, tries server
     * SERVER_FIRST: Tries to load from server first, if unavailable, tries cache
     * CACHE_ONLY: Only tries to read data from cache
     * SERVER_ONLY: Only tries to retrieve data from server
     */
    public enum LoadingStrategy {
        CACHE_FIRST, SERVER_FIRST, CACHE_ONLY, SERVER_ONLY
    }

    /**
     * Default time in milliseconds cached files should be valid.
     */
    private final long DEFAULT_CACHE_MAX_AGE = 1000 * 60 * 60 * 24;

    /**
     * Time in milliseconds cached files should be valid.
     */
    private long cacheMaxAge = DEFAULT_CACHE_MAX_AGE;

    /**
     * Defines all details that are needed for connecting to the specified API.
     */
    private API api;

    /**
     * Listener that is called when data is loaded or error occures.
     */
    private OnDataLoadedListener listener;

    /**
     * Context.
     */
    private Context context;

    /**
     * Method prefix used for debugging purposes to identify which kind of API this is.
     */
    private String methodPrefix;

    /**
     * Initializes context of the loader.
     *
     * @param context  Context for the loader
     */
    public DataLoader(Context context) {
        this.context = context;
    }

    /**
     * Initializes context and API details for the loader.
     *
     * @param context   Context for the loader
     * @param api       API to interact with
     */
    public DataLoader(Context context, API api) {
        this.context = context;

        setApi(api);
    }

    /**
     * Gets API details.
     *
     * @return  API details
     */
    public API getApi() {
        return api;
    }

    /**
     * Sets API details for the loader.
     *
     * @param api   API details
     */
    public void setApi(API api) {
        this.api = api;
        this.methodPrefix = api.getType().name() + ":";
    }

    /**
     * Gets max age of the cache in milliseconds.
     *
     * @return Max age of the cache in milliseconds
     */
    public long getCacheMaxAge() {
        return cacheMaxAge;
    }

    /**
     * Sets max age for the cache in milliseconds.
     *
     * @param cacheMaxAge   Max age for the cache in milliseconds
     */
    public void setCacheMaxAge(long cacheMaxAge) {
        this.cacheMaxAge = cacheMaxAge;
    }

    /**
     * Sets listener for when the loading is completed, either successfully or unsuccessfully.
     *
     * @param listener  Listener to call when data is loaded
     */
    public void setOnDataLoadedListener(OnDataLoadedListener listener) {
        this.listener = listener;
    }

    /**
     * Removes data loaded listener that is attached to this DataLoader.
     */
    public void removeOnDataLoadedListener() {
        this.listener = null;
    }

    /**
     * Starts loading of data based on the specified strategy.
     *
     * @param strategy Which strategy should be used to load data
     */
    public void loadData(LoadingStrategy strategy) {
        switch (strategy) {
            case CACHE_FIRST:
                loadDataFromCache(true);
                break;
            case CACHE_ONLY:
                loadDataFromCache(false);
                break;
            case SERVER_FIRST:
                loadDataFromServer(true);
                break;
            case SERVER_ONLY:
                loadDataFromServer(false);
        }
    }

    /**
     * Loads data from the API and calls listener when completed. Saves loaded data to cache.
     *
     * @param tryCacheOnFailure If enabled, after unsuccessful request cache is used
     */
    private void loadDataFromServer(final boolean tryCacheOnFailure) {
        OkHttpClient client = new OkHttpClient();

        client.newCall(api.getRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Debug.log(methodPrefix + "onFailure()", "Server request failed");

                if (tryCacheOnFailure) {
                    loadDataFromCache(false);
                } else {
                    Debug.log(methodPrefix + "onFailure()", "Triggering loading failure");
                    if (listener != null) listener.onFailure();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Debug.log(methodPrefix + "onResponse()", "Request succeeded");

                    String body = response.body().string();
                    DataCache.write(context, api.getCacheKey(), body);

                    if (listener != null) listener.onSuccess(body);
                } else if (tryCacheOnFailure) {
                    Debug.log(methodPrefix + "onResponse()", "Server request returned error code");
                    loadDataFromCache(false);
                } else {
                    Debug.log(methodPrefix + "onResponse()", "Server request returned error code, " +
                            "triggering loading failure");
                    if (listener != null) listener.onFailure();
                }
            }
        });
    }

    /**
     * Reads data from cache and call listener when completed.
     *
     * @param tryServerOnFailure If enabled, tries to load data from server when cache is unavailable.
     */
    private void loadDataFromCache(boolean tryServerOnFailure) {
        if (DataCache.has(context, api.getCacheKey(), cacheMaxAge)) {
            Debug.log(methodPrefix + "loadDataFromCache()", "Cache available, reading data");
            String cachedData = DataCache.read(context, api.getCacheKey());

            if (listener != null) listener.onSuccess(cachedData);
        } else if (tryServerOnFailure) {
            Debug.log(methodPrefix + "loadDataFromCache()", "Cache not available, trying server");
            loadDataFromServer(false);
        } else {
            Debug.log(methodPrefix + "loadDataFromCache()", "Cache not available, triggering loading failure");
            if (listener != null) listener.onFailure();
        }
    }
}
