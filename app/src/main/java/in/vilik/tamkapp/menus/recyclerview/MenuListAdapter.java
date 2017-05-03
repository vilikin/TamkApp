package in.vilik.tamkapp.menus.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.Menu;
import in.vilik.tamkapp.menus.MenuListItem;

/**
 * Implements an adapter for expandable RecyclerView containing a MenuList.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public class MenuListAdapter extends ExpandableRecyclerAdapter<Menu, MenuListItem, MenuHeaderViewHolder, MenuListItemViewHolder> {

    /**
     * Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac dolor ac lectus bibendum
     * vestibulum eu et enim. Curabitur vel varius diam. Curabitur sapien metus, dapibus a ornare
     * vel, lobortis vel tortor. Lukeeko tätä kukaan? Jussi? Sed molestie consectetur volutpat.
     */
    private LayoutInflater mInflater;

    /**
     * Initializes adapter to work with a certain MenuList.
     *
     * @param context   Context
     * @param menuList  MenuList
     */
    public MenuListAdapter(Context context, @NonNull List<Menu> menuList) {
        super(menuList);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Creates MenuHeaderViewHolder for a parent.
     *
     * @param parentViewGroup   Parent ViewGroup
     * @param viewType          View type
     * @return                  MenuHeaderViewHolder
     */
    @NonNull
    @Override
    public MenuHeaderViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = null;
        MenuListItem.ItemType itemType = MenuListItem.ItemType.values()[viewType];

        switch (itemType) {
            case MENU_HEADER:
                view = mInflater.inflate(R.layout.menu_header, parentViewGroup, false);
                break;
        }

        return new MenuHeaderViewHolder(view);
    }

    /**
     * Determines if certain view type is for a parent.
     *
     * @param viewType  View type
     * @return          If view type is for a parent
     */
    @Override
    public boolean isParentViewType(int viewType) {
        return MenuListItem.ItemType.MENU_HEADER.ordinal() == viewType;
    }

    /**
     * Gets view type of a child.
     *
     * @param parentPosition    Parent position
     * @param childPosition     Child position
     * @return                  View type of the child
     */
    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        return getParentList().get(parentPosition).getChildList().get(childPosition).getItemType().ordinal();
    }

    /**
     * Creates MenuListItemViewHolder for a child.
     *
     * @param childViewGroup    Child ViewGroup
     * @param viewType          View type
     * @return                  MenuListItemViewHolder
     */
    @NonNull
    @Override
    public MenuListItemViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        MenuListItem.ItemType itemType = MenuListItem.ItemType.values()[viewType];
        View view = null;

        switch (itemType) {
            case MEAL_HEADER:
                view = mInflater.inflate(R.layout.meal_header, childViewGroup, false);
                break;
            case MEAL_OPTION:
                view = mInflater.inflate(R.layout.meal_option, childViewGroup, false);
                break;
        }

        return new MenuListItemViewHolder(view);
    }

    /**
     * Binds Menu object to a parent ViewHolder.
     *
     * @param parentViewHolder      Parent ViewHolder
     * @param parentPosition        Parent position
     * @param parent                Menu to bind to the ViewHolder
     */
    @Override
    public void onBindParentViewHolder(@NonNull MenuHeaderViewHolder parentViewHolder, int parentPosition, @NonNull Menu parent) {
        parentViewHolder.bind(parent);
    }

    /**
     * Binds MenuListItem object to a child ViewHolder.
     *
     * @param childViewHolder      Child ViewHolder
     * @param parentPosition       Parent position
     * @param childPosition        Child position
     * @param child                MenuListItem to bind to the ViewHolder
     */
    @Override
    public void onBindChildViewHolder(@NonNull MenuListItemViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull MenuListItem child) {
        childViewHolder.bind(child);
    }
}
