package in.vilik.tamkapp.menus;

import java.util.ArrayList;

/**
 * Implements a class representing a meal.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public class Meal implements MenuListItem {

    /**
     * Type of the meal.
     */
    private String type;

    /**
     * Readable type of the meal.
     */
    private String readableType;

    /**
     * All options associated with this meal type.
     */
    private ArrayList<MealOption> options;

    /**
     * Initializes meal with an empty list of options.
     */
    public Meal() {
        this.options = new ArrayList<>();
    }

    /**
     * Gets readable type of the meal.
     *
     * @return Readable type of the meal
     */
    public String getReadableType() {
        return readableType;
    }

    /**
     * Sets readable type for the meal.
     *
     * @param readableType Readable type for the meal
     */
    public void setReadableType(String readableType) {
        this.readableType = readableType;
    }

    /**
     * Gets type of the meal.
     *
     * @return Type of the meal
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type for the meal.
     *
     * @param type Type for the meal
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets all options associated with the meal.
     *
     * @return All options associated the meal
     */
    public ArrayList<MealOption> getOptions() {
        return options;
    }

    /**
     * Sets all options associated with the meal.
     *
     * @param options All options associated with the meal
     */
    public void setOptions(ArrayList<MealOption> options) {
        this.options = options;
    }

    /**
     * Checks if there are no options associated with the meal.
     *
     * @return  If there are no options associated with the meal
     */
    public boolean isEmpty() {
        return options.isEmpty();
    }

    /**
     * Gets primary text of menu item.
     *
     * @return  Primary text of menu item
     */
    @Override
    public String getPrimaryText() {
        return getReadableType();
    }

    /**
     * Gets secondary text of menu item.
     *
     * @return  Null, as this menu item has no secondary text
     */
    @Override
    public String getSecondaryText() {
        return null;
    }

    /**
     * Gets item type of menu item.
     *
     * @return  MEAL_HEADER item type
     */
    @Override
    public ItemType getItemType() {
        return ItemType.MEAL_HEADER;
    }
}