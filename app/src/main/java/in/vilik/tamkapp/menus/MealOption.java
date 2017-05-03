package in.vilik.tamkapp.menus;

import java.util.List;

/**
 * Implements a class representing a meal option.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public class MealOption implements MenuListItem {

    /**
     * Name of the meal option.
     */
    private String name;

    /**
     * Extra details of the meal option.
     */
    private String details;

    /**
     * List of diets the meal is suitable for.
     */
    private List<Diet> diets;

    /**
     * If the diets should be displayed in the menu.
     */
    private boolean showDiets;

    /**
     * Initializes meal option with a name.
     *
     * @param name  Name for the meal option
     */
    public MealOption(String name) {
        this.name = name;
    }

    /**
     * Checks if diets should be shown in the menu or not.
     *
     * @return If diets should be shown in the menu or not
     */
    public boolean areDietsShown() {
        return showDiets;
    }

    /**
     * Sets diets visibility in the menu.
     *
     * @param showDiets If diets should be visible or not
     */
    public void setDietsShown(boolean showDiets) {
        this.showDiets = showDiets;
    }

    /**
     * Gets name of the meal option.
     *
     * @return Name of the meal option
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for the meal option.
     *
     * @param name Name for the meal option
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets extra details of the meal option.
     *
     * @return Extra details of the meal option
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets extra details for the meal option.
     *
     * @return Extra details for the meal option
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Gets list of diets associated with the meal option.
     *
     * @return List of diets associated with the meal option
     */
    public List<Diet> getDiets() {
        return diets;
    }

    /**
     * Sets list of diets associated with the meal option.
     *
     * @param diets List of diets associated with the meal option
     */
    public void setDiets(List<Diet> diets) {
        this.diets = diets;
    }

    /**
     * Gets formatted list of diets associated with the meal option.
     *
     * @return Formatted list of diets associated with the meal option
     */
    private String getDietsFormatted() {
        StringBuilder sb = new StringBuilder();

        for (Diet diet : diets) {
            sb.append(", ").append(diet.getAbbreviation());
        }

        return sb.toString().substring(2);
    }

    /**
     * Gets primary text of menu item.
     *
     * @return  Meal option name and possibly associated diets
     */
    @Override
    public String getPrimaryText() {
        if (areDietsShown() && !diets.isEmpty()) {
            return getName() + " (" + getDietsFormatted() + ")";
        } else {
            return getName();
        }
    }

    /**
     * Gets secondary text of menu item.
     *
     * @return  Extra details of the meal option, if there's any
     */
    @Override
    public String getSecondaryText() {
        return getDetails();
    }

    /**
     * Gets item type of menu item.
     *
     * @return  MEAL_OPTION item type
     */
    @Override
    public ItemType getItemType() {
        return ItemType.MEAL_OPTION;
    }
}
