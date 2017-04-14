package in.vilik.tamkapp.menus;

/**
 * Created by vili on 10/04/2017.
 */

public class MealOption implements MenuListItem {
    private String name;
    private String details;

    public MealOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasDetails() {
        return details != null;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String getPrimaryText() {
        return getName();
    }

    @Override
    public String getSecondaryText() {
        return getDetails();
    }

    @Override
    public ItemType getItemType() {
        return ItemType.MEAL_OPTION;
    }
}
