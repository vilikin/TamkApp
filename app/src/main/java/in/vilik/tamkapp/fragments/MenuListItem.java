package in.vilik.tamkapp.fragments;

/**
 * Created by vili on 13/04/2017.
 */

public interface MenuListItem {
    enum ItemType {
        MENU_HEADER, MEAL_HEADER, MEAL_OPTION
    }

    String getPrimaryText();
    String getSecondaryText();
    ItemType getItemType();
}
