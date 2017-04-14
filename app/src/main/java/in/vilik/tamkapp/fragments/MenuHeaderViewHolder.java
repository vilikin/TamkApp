package in.vilik.tamkapp.fragments;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import org.w3c.dom.Text;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.Menu;

/**
 * Created by vili on 13/04/2017.
 */

public class MenuHeaderViewHolder extends ParentViewHolder {
    TextView primaryTextView;

    public MenuHeaderViewHolder(View itemView) {
        super(itemView);

        primaryTextView = (TextView)itemView.findViewById(R.id.menu_header_text);
    }

    public void bind(MenuListItem menuListItem) {
        primaryTextView.setText(menuListItem.getPrimaryText());
    }
}
