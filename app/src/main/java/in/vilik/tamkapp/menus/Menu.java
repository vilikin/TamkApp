package in.vilik.tamkapp.menus;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by vili on 10/04/2017.
 */

public class Menu {
    private Date date;
    private ArrayList<Meal> meals;
    private boolean empty;

    public Menu() {
        this.meals = new ArrayList<>();
    }

    public Menu(Date date, ArrayList<Meal> meals) {
        this.date = date;
        setMeals(meals);
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

            if (!Meal.showMealType(meal.getType())) {
                iterator.remove();
            }
        }

        if (meals.isEmpty() ||
                (meals.size() == 1 && meals.get(0).isEmpty())) {
            this.empty = true;
        }

        this.meals = meals;
    }

    public boolean isEmpty() {
        return empty;
    }
}
