package in.vilik.tamkapp.menus.recyclerview;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.MenuListItem;

/**
 * Implements a ViewHolder for parent components of the menu list.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
class MenuHeaderViewHolder extends ParentViewHolder {

    /**
     * Primary text view of the ViewHolder.
     */
    private TextView primaryTextView;

    /**
     * Expand icon.
     */
    private ImageView expandIcon;

    /**
     * Initializes ViewHolder with an item view.
     *
     * @param itemView  Item view
     */
    public MenuHeaderViewHolder(View itemView) {
        super(itemView);

        primaryTextView = (TextView)itemView.findViewById(R.id.menu_header_text);
        expandIcon = (ImageView)itemView.findViewById(R.id.expandIcon);
    }

    /**
     * Toggles expand icon rotation based on whether parent is expanded or not.
     *
     * @param expanded  If parent is expanded or not
     */
    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        ObjectAnimator animator;

        if (expanded) {
            animator = ObjectAnimator.ofFloat(expandIcon, "rotation", 180, 0);
        } else {
            animator = ObjectAnimator.ofFloat(expandIcon, "rotation", 0, 180);
        }

        animator.setDuration(500);

        animator.start();
    }

    /**
     * Binds MenuListItem to the view.
     *
     * @param menuListItem  MenuListItem to bind to the view
     */
    void bind(MenuListItem menuListItem) {
        primaryTextView.setText(menuListItem.getPrimaryText());
    }
}
