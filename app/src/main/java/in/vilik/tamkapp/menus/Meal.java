package in.vilik.tamkapp.menus;

import java.util.ArrayList;

/**
 * Created by vili on 10/04/2017.
 */

public class Meal implements MenuListItem {
    private String type;
    private String readableType;
    private boolean empty;

    private ArrayList<MealOption> options;

    public static boolean showMealType(String mealType) {
        return true;
    }

    public Meal() {
        this.options = new ArrayList<>();
    }

    public String getReadableType() {
        return readableType;
    }

    public void setReadableType(String readableType) {
        this.readableType = readableType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<MealOption> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<MealOption> options) {
        if (options.isEmpty()) {
            this.empty = true;
        }

        this.options = options;
    }

    public boolean isEmpty() {
        return empty;
    }

    @Override
    public String getPrimaryText() {
        return getReadableType();
    }

    @Override
    public String getSecondaryText() {
        return null;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.MEAL_HEADER;
    }
}