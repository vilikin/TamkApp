package in.vilik.tamkapp.menus;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import in.vilik.tamkapp.utils.AppPreferences;
import in.vilik.tamkapp.utils.DateUtil;


/**
 * Implements a class representing a menu of a single date.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public class Menu implements MenuListItem, Parent<MenuListItem> {

    /**
     * Date of the menu.
     */
    private Date date;

    /**
     * List of meals associated with the menu.
     */
    private ArrayList<Meal> meals;

    /**
     * Flag: if menu is empty or not.
     */
    private boolean empty;

    /**
     * Parent MenuList of the menu.
     */
    private MenuList parentMenuList;

    /**
     * Set of meals that should be shown in the menu.
     */
    private Set<String> includedMeals;

    /**
     * Initializes menu with parent MenuList.
     *
     * @param parentMenuList Parent MenuList
     */
    public Menu(MenuList parentMenuList) {
        this.parentMenuList = parentMenuList;

        AppPreferences preferences = new AppPreferences(parentMenuList.getContext());

        switch (getParentMenuList().getApiType()) {
            case CAMPUSRAVITA_MENU:
                includedMeals = preferences.getCampusravitaMeals();
                break;
            case PIRTERIA_MENU:
                includedMeals = preferences.getPirteriaMeals();
                break;
        }

        this.meals = new ArrayList<>();
    }

    /**
     * Gets parent MenuList.
     *
     * @return Parent MenuList
     */
    public MenuList getParentMenuList() {
        return parentMenuList;
    }

    /**
     * Gets date of the menu.
     *
     * @return  Date of the menu
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date for the menu.
     *
     * @param date Date for the menu
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets all meals associated with the menu.
     *
     * @return All meals associated with the menu
     */
    public ArrayList<Meal> getMeals() {
        return meals;
    }

    /**
     * Sets meals associated with the menu.
     *
     * Removes meals that are not part of included meals that are selected in preferences.
     *
     * @param meals Meals associated with the menu
     */
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

    /**
     * Checks if certain meal type should be visible in the menu.
     *
     * @param type  Type for visibility check
     * @return      If it should be visible
     */
    private boolean showMealType(String type) {
        if (includedMeals != null) {
            return includedMeals.contains(type);
        } else {
            return true;
        }
    }

    /**
     * Checks if menu is empty.
     *
     * @return If menu is empty or not
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Gets primary text of menu item.
     *
     * @return  Formatted date of the menu
     */
    @Override
    public String getPrimaryText() {
        return DateUtil.formatDate(parentMenuList.getContext(), date, DateUtil.DateFormat.ON_DAY);
    }

    /**
     * Gets secondary text of menu item.
     *
     * @return  Null, as there is no secondary text for menus
     */
    @Override
    public String getSecondaryText() {
        return null;
    }

    /**
     * Gets item type of menu item.
     *
     * @return  MENU_HEADER item type
     */
    @Override
    public ItemType getItemType() {
        return ItemType.MENU_HEADER;
    }

    /**
     * Gets child list associated with the menu in RecyclerView.
     *
     * @return  List of MenuListItems associated with the menu
     */
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

    /**
     * Determines if the menu is initially expanded in the RecyclerView.
     *
     * Only first menu of the parent MenuList is expanded initially.
     *
     * @return If menu is initially expanded in the RecyclerView
     */
    @Override
    public boolean isInitiallyExpanded() {
        return getParentMenuList().getMenus().get(0) == this;
    }
}
