package in.vilik.tamkapp.menus;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import in.vilik.tamkapp.fragments.SettingsFragment;


/**
 * Created by vili on 10/04/2017.
 */

public class Menu {
    private Date date;
    private ArrayList<Meal> meals;
    private boolean empty;
    private MenuList parentMenuList;
    private Set<String> includedMeals;

    public Menu(MenuList parentMenuList) {
        this.parentMenuList = parentMenuList;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(parentMenuList.getContext());

        switch (getParentMenuList().getMenuType()) {
            case CAMPUSRAVITA:
                includedMeals = prefs.getStringSet(SettingsFragment.CAMPUSRAVITA_INCLUDED_MEALS, null);
                break;
            case PIRTERIA:
                includedMeals = prefs.getStringSet(SettingsFragment.PIRTERIA_INCLUDED_MEALS, null);
                break;
        }

        this.meals = new ArrayList<>();
    }

    public MenuList getParentMenuList() {
        return parentMenuList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        Iterator<Meal> iterator = meals.iterator();

        while (iterator.hasNext()) {
            Meal meal = iterator.next();

            if (!showMealType(meal.getType())) {
                iterator.remove();
            }
        }

        if (meals.isEmpty() ||
                (meals.size() == 1 && meals.get(0).isEmpty())) {
            this.empty = true;
        }

        this.meals = meals;
    }

    private boolean showMealType(String type) {
        if (includedMeals != null) {
            return includedMeals.contains(type);
        } else {
            return true;
        }
    }

    public boolean isEmpty() {
        return empty;
    }
}
