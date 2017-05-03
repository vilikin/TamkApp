package in.vilik.tamkapp.menus;

/**
 * Defines an interface for building blocks of menu RecyclerView.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public interface MenuListItem {

    /**
     * Defines all available menu item types.
     */
    enum ItemType {
        MENU_HEADER, MEAL_HEADER, MEAL_OPTION
    }

    /**
     * Gets primary text of menu item.
     *
     * @return  Primary text of menu item
     */
    String getPrimaryText();

    /**
     * Gets secondary text of menu item.
     *
     * Notice that all items might not have secondary text. In this case, null is returned.
     *
     * @return  Secondary text of menu item
     */
    String getSecondaryText();

    /**
     * Gets item type of menu item.
     *
     * @return  Item type of menu item
     */
    ItemType getItemType();
}
