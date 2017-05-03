package in.vilik.tamkapp.menus.recyclerview;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.MenuListItem;

/**
 * Implements a ViewHolder for child components of the menu list.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
class MenuListItemViewHolder extends ChildViewHolder {

    /**
     * Primary text view.
     */
    private TextView primaryTextView;

    /**
     * Secondary text view.
     */
    private TextView secondaryTextView;

    /**
     * Initializes ViewHolder with an item view.
     *
     * @param itemView  Item view
     */
    public MenuListItemViewHolder(View itemView) {
        super(itemView);

        primaryTextView = (TextView)itemView.findViewById(R.id.menu_list_item_header);
        secondaryTextView = (TextView)itemView.findViewById(R.id.menu_list_item_description);
    }

    /**
     * Binds MenuListItem to the view.
     *
     * Primary text is always set. Secondary text is set when it's not null.
     * Otherwise secondary text field is hidden.
     *
     * @param menuListItem  MenuListItem to bind to the view
     */
    public void bind(MenuListItem menuListItem) {
        primaryTextView.setText(menuListItem.getPrimaryText());

        if (menuListItem.getSecondaryText() != null) {
            secondaryTextView.setText(menuListItem.getSecondaryText());
        } else if (menuListItem.getItemType() == MenuListItem.ItemType.MEAL_OPTION) {
            secondaryTextView.setVisibility(View.GONE);
        }
    }
}
