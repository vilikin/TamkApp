package in.vilik.tamkapp.utils;


import android.content.Context;

import java.io.IOException;

import in.vilik.tamkapp.Debug;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by vili on 16/04/2017.
 */

public abstract class DataLoader {
    enum LoadingStatus {
        SUCCESSFULLY_LOADED, LOADING_ERROR, LOADING_IN_PROGRESS
    }

    public enum LoadingStrategy {
        CACHE_FIRST, SERVER_FIRST, CACHE_ONLY, SERVER_ONLY
    }

    private final long DEFAULT_CACHE_MAX_AGE = 1000 * 60 * 60 * 24;

    private API api;
    private LoadingStatus status;
    private OnDataLoadedListener listener;
    private long cacheMaxAge = DEFAULT_CACHE_MAX_AGE;

    private Context context;

    private String methodPrefix;

    public DataLoader(Context context) {
        this.context = context;
    }

    public DataLoader(Context context, API api) {
        this.context = context;

        setApi(api);
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
        this.methodPrefix = api.getType().name() + ":";
    }

    public long getCacheMaxAge() {
        return cacheMaxAge;
    }

    public void setCacheMaxAge(long cacheMaxAge) {
        this.cacheMaxAge = cacheMaxAge;
    }

    public void setOnDataLoadedListener(OnDataLoadedListener listener) {
        this.listener = listener;
    }

    public void removeOnDataLoadedListener() {
        this.listener = null;
    }

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
