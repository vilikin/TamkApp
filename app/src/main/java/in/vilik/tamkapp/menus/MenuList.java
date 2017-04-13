package in.vilik.tamkapp.menus;

import android.content.Context;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import in.vilik.tamkapp.Debug;
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
    private boolean loaded;

    public MenuList(Context context) {
        this.context = context;
        this.menus = new ArrayList<>();

        Debug.log("MenuList()", "New MenuList being generated, initiating refresh");
        refresh();
    }

    public void setOnMenuLoadedListener(OnMenuLoadedListener listener, boolean triggerIfAlreadyLoaded) {
        this.listener = listener;

        if (loaded && triggerIfAlreadyLoaded) {
            listener.loaded();
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

    abstract String getUrl();
    abstract MenuType getMenuType();
    abstract void handleSuccessfulResponse(String response);

    public void refresh() {
        Debug.log("refresh()", "NEW SERVER REQUEST: " + getUrl());

        Request request = new Request.Builder()
                .url(getUrl())
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                updateError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    MenuCache.writeToFile(context, getMenuType(), body);

                    handleSuccessfulResponse(body);
                    loaded = true;

                    if (listener != null) {
                        listener.loaded();
                    }
                } else {
                    updateError();
                }
            }
        });
    }

    void updateError() {
        // TODO: SHOW USER AN ERROR MSG?
        System.out.println("ERROR WHILE UPDATING");
    }
}
