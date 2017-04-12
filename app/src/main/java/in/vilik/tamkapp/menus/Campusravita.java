package in.vilik.tamkapp.menus;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.vilik.tamkapp.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vili on 10/04/2017.
 */

public class Campusravita extends MenuList {
    private final String API_URL = "http://vilik.in:3000/menu";

    public Campusravita(Context context) {
        super(context);
    }

    @Override
    String getUrl() {
        return API_URL;
    }

    @Override
    void handleSuccessfulResponse(String response) {
        ArrayList<Menu> menus = getMenus();

        menus.clear();

        try {
            JSONArray menuList = new JSONArray(response);
            menus.clear();

            Date now = new Date();

            for (int i = 0; i < menuList.length(); i++) {
                Menu m = parseMenu(menuList.getJSONObject(i));

                if (m.getDate().after(now)) {
                    menus.add(m);
                }
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            updateError();
        }
    }

    private Menu parseMenu(JSONObject json) throws JSONException, ParseException {
        Menu menu = new Menu();

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        Date date = df1.parse(json.getString("date"));

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        c.set(Calendar.HOUR_OF_DAY, 18);
        c.set(Calendar.MINUTE, 0);

        menu.setDate(c.getTime());

        ArrayList<Meal> meals = new ArrayList<>();

        JSONArray mealsJson = json.getJSONArray("meals");

        for (int i = 0; i < mealsJson.length(); i++) {
            meals.add(parseMeal(mealsJson.getJSONObject(i)));
        }

        menu.setMeals(meals);

        return menu;
    }

    private Meal parseMeal(JSONObject json) throws JSONException {
        Meal meal = new Meal();

        String type = MealTypes.getMealTypeForJson(getContext(),
                R.array.campusravita_meals_json, R.array.campusravita_meals,
                json.getString("name"));

        meal.setType(type);

        String readableType = MealTypes.getReadableMealType(getContext(),
                R.array.campusravita_meals, R.array.campusravita_meals_readable ,
                type);

        meal.setReadableType(readableType);

        ArrayList<MealOption> options = new ArrayList<>();
        JSONArray optionsJson = json.getJSONArray("options");

        for (int i = 0; i < optionsJson.length(); i++) {
            options.add(parseOption(optionsJson.getJSONObject(i)));
        }

        meal.setOptions(options);

        return meal;
    }

    private MealOption parseOption(JSONObject json) throws JSONException {
        MealOption option = new MealOption(json.getString("name"));

        if (json.has("details")) {
            option.setDetails(json.getString("details"));
        }

        return option;
    }
}
