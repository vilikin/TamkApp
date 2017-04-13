package in.vilik.tamkapp.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.MenuList;

/**
 * Created by vili on 13/04/2017.
 */

public class MenuListAdapter extends RecyclerView.Adapter<MenuListViewHolder> {
    private MenuList menuList;
    private ArrayList<MenuListAdapterItem> adapterItems;
    public static final int TYPE_MENU_HEADER = 0;
    public static final int TYPE_MEAL_HEADER = 1;
    public static final int TYPE_MEAL_OPTION = 2;
    public static final int TYPE_NO_CONTENT = 3;


    public MenuListAdapter(MenuList menuList) {
        this.menuList = menuList;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_MENU_HEADER;
    }

    @Override
    public MenuListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_MENU_HEADER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_header, parent, false);
                return new MenuHeaderViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(MenuListViewHolder holder, int position) {
        holder.getPrimaryTextView().setText(menuList.getMenus().get(position).getPrimaryText());
    }

    @Override
    public int getItemCount() {
        return menuList.getMenus().size();
    }
}
