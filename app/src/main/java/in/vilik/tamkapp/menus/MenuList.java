package in.vilik.tamkapp.menus;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.utils.ErrorToaster;
import in.vilik.tamkapp.utils.MenuCache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vili on 10/04/2017.
 */

public abstract class MenuList {
    private ArrayList<Menu> menus;
    private Context context;
    private OnMenuLoadedListener listener;
    private LoadingStatus status;
    private MenuType menuType;

    public enum LoadingStatus {
        SUCCESSFULLY_LOADED, LOADING_ERROR, PARSING_ERROR, LOADING_IN_PROGRESS
    }

    private static final long CACHED_MENU_MAX_AGE = 1000 * 60 * 60 * 24; // 1 day

    public MenuList(Context context, MenuType menuType) {
        this.context = context;
        this.menuType = menuType;
        this.menus = new ArrayList<>();

        Debug.log("MenuList()", "New MenuList being generated, initiating loadFromServer");

        if (MenuCache.has(context, menuType, CACHED_MENU_MAX_AGE)) {
            loadFromCache(false, true);
        } else {
            loadFromServer(false);
        }
    }

    public void setOnMenuLoadedListener(OnMenuLoadedListener listener, boolean triggerIfAlreadyLoaded) {
        this.listener = listener;

        if (status != LoadingStatus.LOADING_IN_PROGRESS && triggerIfAlreadyLoaded) {
            switch (status) {
                case SUCCESSFULLY_LOADED:
                    listener.onSuccess();
                    break;
                case LOADING_ERROR:
                    listener.onError(ErrorToaster.ERROR_FAILED_TO_LOAD);
                    break;
                case PARSING_ERROR:
                    listener.onError(ErrorToaster.ERROR_FAILED_TO_PARSE);
                    break;
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    ArrayList<Menu> getMenus() {
        return menus;
    }

    public int getCount() {
        return menus.size();
    }

    public Menu getMenu(int position) {
        return menus.get(position);
    }

    public MenuType getMenuType() {
        return menuType;
    }

    abstract String getUrl();
    abstract boolean handleSuccessfulResponse(String response);

    public void loadFromServer(final boolean tryCacheOnFailure) {
        status = LoadingStatus.LOADING_IN_PROGRESS;

        Debug.log("loadFromServer()", "NEW SERVER REQUEST: " + getUrl());

        Request request = new Request.Builder()
                .url(getUrl())
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Debug.log("onFailure()", "Server request failed");

                if (tryCacheOnFailure) {
                    loadFromCache(true, false);
                } else {
                    loadingFinished(LoadingStatus.LOADING_ERROR);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Debug.log("onResponse()", "Request succeeded");

                    String body = response.body().string();

                    boolean successfullyParsed = handleSuccessfulResponse(body);

                    if (successfullyParsed) {
                        MenuCache.write(context, menuType, body);
                    }

                    loadingFinished(successfullyParsed ?
                            LoadingStatus.SUCCESSFULLY_LOADED : LoadingStatus.PARSING_ERROR);
                } else {
                    loadingFinished(LoadingStatus.LOADING_ERROR);
                }
            }
        });
    }

    public void loadFromCache(boolean considerMaxAge, boolean tryServerOnFailure) {
        status = LoadingStatus.LOADING_IN_PROGRESS;

        Debug.log("loadFromCache()", "Attempting to load menu from cache");

        if (!considerMaxAge || MenuCache.has(context, menuType, CACHED_MENU_MAX_AGE)) {
            String cachedMenu = MenuCache.read(context, menuType);
            boolean successfullyParsed = handleSuccessfulResponse(cachedMenu);

            loadingFinished(successfullyParsed ?
                    LoadingStatus.SUCCESSFULLY_LOADED : LoadingStatus.PARSING_ERROR);
        } else if (tryServerOnFailure) {
            Debug.log("loadFromCache()", "Cache not available, trying server");
            loadFromServer(false);
        } else {
            Debug.log("loadFromCache()", "Cache not available, triggering loading error");
            loadingFinished(LoadingStatus.LOADING_ERROR);
        }
    }

    private void loadingFinished(LoadingStatus loadingStatus) {
        status = loadingStatus;

        switch (status) {
            case SUCCESSFULLY_LOADED:
                Debug.log("loadingFinished()", "Loading finished successfully");
                if (listener != null) listener.onSuccess();
                break;
            case LOADING_ERROR:
                Debug.log("loadingFinished()", "Loading failed");
                if (listener != null) listener.onError(ErrorToaster.ERROR_FAILED_TO_LOAD);
                break;
            case PARSING_ERROR:
                Debug.log("loadingFinished()", "Parsing failed");
                if (listener != null) listener.onError(ErrorToaster.ERROR_FAILED_TO_PARSE);
                break;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Menu menu : menus) {
            if (!menu.isEmpty()) {
                sb.append(menu.getDate());
                sb.append("\n--------------------------------\n");

                for (Meal meal : menu.getMeals()) {
                    sb.append(meal.getReadableType());
                    sb.append("\n");

                    for (MealOption mealOption : meal.getOptions()) {
                        sb.append("--- ");
                        sb.append(mealOption.getName());
                        sb.append("\n");

                        if (mealOption.getDetails() != null) {
                            sb.append("------ ");
                            sb.append(mealOption.getDetails());
                            sb.append("\n");
                        }
                    }
                }

                sb.append("--------------------------------\n\n");
            }
        }

        return sb.toString();
    }
}
