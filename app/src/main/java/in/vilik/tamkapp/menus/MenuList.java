package in.vilik.tamkapp.menus;

import android.content.Context;

import java.util.ArrayList;

import in.vilik.tamkapp.utils.API;
import in.vilik.tamkapp.utils.DataLoader;
import in.vilik.tamkapp.utils.OnDataLoadedListener;

/**
 * Created by vili on 10/04/2017.
 */
public abstract class MenuList extends DataLoader {
    private ArrayList<Menu> menus;
    private Context context;
    private API.Type apiType;
    private OnMenuUpdatedListener listener;

    public MenuList(Context context, API api) {
        super(context, api);

        this.apiType = api.getType();
        this.context = context;
        this.menus = new ArrayList<>();

        setOnDataLoadedListener(new OnDataLoadedListener() {
            @Override
            public void onSuccess(String data) {
                boolean parsedSuccessfully = handleSuccessfulResponse(data);
                triggerOnMenuUpdatedListener(parsedSuccessfully);
            }

            @Override
            public void onFailure() {
                triggerOnMenuUpdatedListener(false);
            }
        });
    }

    private void triggerOnMenuUpdatedListener(boolean success) {
        if (success && listener != null) {
            listener.onSuccess();
        } else if (listener != null) {
            listener.onError();
        }
    }

    public void setOnMenuUpdatedListener(OnMenuUpdatedListener listener) {
        this.listener = listener;
    }

    public void removeOnMenuUpdatedListener() {
        this.listener = null;
    }

    abstract boolean handleSuccessfulResponse(String response);

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public API.Type getApiType() {
        return apiType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Menu menu : menus) {
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

        return sb.toString();
    }
}
