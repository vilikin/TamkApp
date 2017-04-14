package in.vilik.tamkapp.fragments;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import in.vilik.tamkapp.R;

/**
 * Created by vili on 14/04/2017.
 */

public class MenuListItemViewHolder extends ChildViewHolder {
    TextView primaryTextView;
    TextView secondaryTextView;

    public MenuListItemViewHolder(View itemView) {
        super(itemView);

        primaryTextView = (TextView)itemView.findViewById(R.id.menu_list_item_header);
        secondaryTextView = (TextView)itemView.findViewById(R.id.menu_list_item_description);
    }

    public void bind(MenuListItem menuListItem) {
        primaryTextView.setText(menuListItem.getPrimaryText());

        if (menuListItem.getSecondaryText() != null) {
            secondaryTextView.setText(menuListItem.getSecondaryText());
        } else if (menuListItem.getItemType() == MenuListItem.ItemType.MEAL_OPTION) {
            secondaryTextView.setVisibility(View.GONE);
        }
    }
}
