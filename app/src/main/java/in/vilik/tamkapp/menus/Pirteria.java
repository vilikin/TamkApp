package in.vilik.tamkapp.menus;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.R;
import in.vilik.tamkapp.utils.API;
import okhttp3.Request;

/**
 * Created by vili on 10/04/2017.
 */

public class Pirteria extends MenuList {

    public Pirteria(Context context) {
        super(context, new API() {
            @Override
            public Request getRequest() {
                return new Request.Builder()
                        .get()
                        .url("http://www.amica.fi/modules/json/json/Index?costNumber=0823&language=fi")
                        .build();
            }

            @Override
            public Type getType() {
                return Type.PIRTERIA_MENU;
            }
        });
    }

    @Override
    boolean handleSuccessfulResponse(String response) {
        ArrayList<Menu> menus = getMenus();

        menus.clear();

        try {
            JSONObject menu = new JSONObject(response);
            JSONArray menuList = menu.getJSONArray("MenusForDays");
            menus.clear();

            Date now = new Date();

            for (int i = 0; i < menuList.length(); i++) {
                Menu m = parseMenu(menuList.getJSONObject(i));

                if (m.getDate().after(now) && !m.isEmpty()) {
                    menus.add(m);
                }
            }

            return true;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Menu parseMenu(JSONObject json) throws JSONException, ParseException {
        Menu menu = new Menu(this);

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
        Date date = df1.parse(json.getString("Date"));

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.set(Calendar.HOUR_OF_DAY, 18);
        c.set(Calendar.MINUTE, 0);

        menu.setDate(c.getTime());

        ArrayList<Meal> meals = new ArrayList<>();

        JSONArray mealsJson = json.getJSONArray("SetMenus");

        for (int i = 0; i < mealsJson.length(); i++) {
            Meal meal = parseMeal(mealsJson.getJSONObject(i));
            boolean combinedWithPrevious = false;

            for (Meal existingMeal : meals) {
                if (existingMeal.getType().equals(meal.getType())) {
                    existingMeal.getOptions().add(meal.getOptions().get(0));
                    combinedWithPrevious = true;
                }
            }

            if (!combinedWithPrevious) {
                meals.add(parseMeal(mealsJson.getJSONObject(i)));
            }
        }

        menu.setMeals(meals);

        return menu;
    }

    private Meal parseMeal(JSONObject json) throws JSONException {
        Meal meal = new Meal();

        String type = MealTypes.getMealTypeForJson(getContext(),
                R.array.pirteria_meals_json, R.array.pirteria_meals,
                json.getString("Name"));

        meal.setType(type);

        String readableType = MealTypes.getReadableMealType(getContext(),
                R.array.pirteria_meals, R.array.pirteria_meals_readable ,
                type);

        meal.setReadableType(readableType);

        ArrayList<MealOption> options = new ArrayList<>();
        JSONArray optionsJson = json.getJSONArray("Components");

        String name = "";
        String details = "";

        for (int i = 0; i < optionsJson.length(); i++) {
            if (i == 0) {
                name = optionsJson.getString(i);
            } else {
                details += details.equals("") ? optionsJson.getString(i) : ", " + optionsJson.getString(i);
            }
        }

        MealOption mealOption = new MealOption(name);

        if (!details.isEmpty()) {
            mealOption.setDetails(details);
        }

        options.add(mealOption);

        meal.setOptions(options);

        return meal;
    }
}
