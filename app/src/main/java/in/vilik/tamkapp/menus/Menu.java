package in.vilik.tamkapp.menus;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.fragments.SettingsFragment;


/**
 * Created by vili on 10/04/2017.
 */

public class Menu implements MenuListItem, Parent<MenuListItem> {
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

    @Override
    public String getPrimaryText() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);

        Context context = getParentMenuList().getContext();
        String[] weekdays = context.getResources().getStringArray(R.array.weekdays);

        return weekdays[dayOfWeek - 1] + " " + dayOfMonth + "." + month;
    }

    @Override
    public String getSecondaryText() {
        return null;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.MENU_HEADER;
    }

    @Override
    public List<MenuListItem> getChildList() {
        ArrayList<MenuListItem> menuListItems = new ArrayList<>();

        if (!isEmpty()) {
            for (Meal meal : meals) {
                menuListItems.add(meal);

                for (MealOption mealOption : meal.getOptions()) {
                    menuListItems.add(mealOption);
                }
            }
        }

        return menuListItems;
    }

    @Override
    public boolean isInitiallyExpanded() {
        long nowPlus24h = new Date().getTime() + 1000 * 60 * 60 * 24;
        Date tomorrow = new Date();
        tomorrow.setTime(nowPlus24h);

        return date.before(tomorrow);
    }
}
