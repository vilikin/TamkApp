package in.vilik.tamkapp.fragments;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.MenuListItem;

/**
 * Created by vili on 13/04/2017.
 */

public class MenuHeaderViewHolder extends ParentViewHolder {
    private TextView primaryTextView;
    private ImageView expandIcon;

    MenuHeaderViewHolder(View itemView) {
        super(itemView);

        primaryTextView = (TextView)itemView.findViewById(R.id.menu_header_text);
        expandIcon = (ImageView)itemView.findViewById(R.id.expandIcon);
    }

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

    void bind(MenuListItem menuListItem) {
        primaryTextView.setText(menuListItem.getPrimaryText());
    }
}
