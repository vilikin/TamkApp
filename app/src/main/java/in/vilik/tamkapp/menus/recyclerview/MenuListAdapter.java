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
 * Created by vili on 13/04/2017.
 */

public class MenuListAdapter extends ExpandableRecyclerAdapter<Menu, MenuListItem, MenuHeaderViewHolder, MenuListItemViewHolder>{
    private LayoutInflater mInflater;

    public MenuListAdapter(Context context, @NonNull List<Menu> menuList) {
        super(menuList);
        mInflater = LayoutInflater.from(context);
    }

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

    @Override
    public boolean isParentViewType(int viewType) {
        return MenuListItem.ItemType.MENU_HEADER.ordinal() == viewType;
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        return getParentList().get(parentPosition).getChildList().get(childPosition).getItemType().ordinal();
    }

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

    @Override
    public void onBindParentViewHolder(@NonNull MenuHeaderViewHolder parentViewHolder, int parentPosition, @NonNull Menu parent) {
        parentViewHolder.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(@NonNull MenuListItemViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull MenuListItem child) {
        childViewHolder.bind(child);
    }
}
