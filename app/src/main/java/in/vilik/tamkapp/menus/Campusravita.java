package in.vilik.tamkapp.menus;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.utils.API;
import in.vilik.tamkapp.utils.AppPreferences;
import okhttp3.Request;

/**
 * Implements a MenuList for Campusravita.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public class Campusravita extends MenuList {

    /**
     * Preferences of the app.
     */
    private AppPreferences preferences;

    /**
     * Initializes MenuList to interact with Campusravita API.
     *
     * @param context   Context
     * @param language  Language for the MenuList
     */
    public Campusravita(Context context, final String language) {
        super(context, new API() {
            @Override
            public Request getRequest() {
                return new Request.Builder().get()
                        .url("http://vilik.in:3000/menu?lang=" + language).build();
            }

            @Override
            public Type getType() {
                return Type.CAMPUSRAVITA_MENU;
            }

            @Override
            public String getCacheKey() {
                return "campusravita_" + language;
            }
        });

        this.preferences = new AppPreferences(context);
    }

    /**
     * Parses received response and converts it to a list of menus.
     *
     * @param response  Response body
     * @return          If parsing succeeded or not
     */
    boolean handleSuccessfulResponse(String response) {
        ArrayList<Menu> menus = getMenus();

        menus.clear();

        try {
            JSONArray menuList = new JSONArray(response);
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

    /**
     * Parses JSON representation of a Menu.
     *
     * @param json              JSON representation of a Menu
     * @return                  Menu object
     * @throws JSONException    Thrown when parsing of JSON fails
     * @throws ParseException   Thrown when parsing of a date fails
     */
    private Menu parseMenu(JSONObject json) throws JSONException, ParseException {
        Menu menu = new Menu(this);

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

    /**
     * Parses JSON representation of a Meal.
     *
     * @param json              JSON representation of a meal
     * @return                  Meal object
     * @throws JSONException    Thrown when parsing of JSON fails
     */
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

    /**
     * Parses JSON representation of a MealOption.
     *
     * @param json              JSON representation of a MealOption
     * @return                  MealOption object
     * @throws JSONException    Thrown when parsing of JSON fails
     */
    private MealOption parseOption(JSONObject json) throws JSONException {
        MealOption option = new MealOption(json.getString("name"));

        List<Diet> diets = new ArrayList<>();

        JSONArray dietsJson = json.getJSONArray("diets");

        for (int i = 0; i < dietsJson.length(); i++) {
            diets.add(parseDiet(dietsJson.getJSONObject(i)));
        }

        option.setDiets(diets);

        option.setDietsShown(preferences.areDietsShown());

        if (json.has("details")) {
            option.setDetails(json.getString("details"));
        }

        return option;
    }

    /**
     * Parses JSON representation of a Diet.
     *
     * @param json              JSON representation of a Diet
     * @return                  Diet object
     * @throws JSONException    Thrown when parsing of JSON fails
     */
    private Diet parseDiet(JSONObject json) throws JSONException {
        Diet diet = new Diet();
        diet.setName(json.getString("name"));
        diet.setAbbreviation(json.getString("abbreviation"));

        return diet;
    }
}
