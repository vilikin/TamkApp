package in.vilik.tamkapp.fragments;

import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import in.vilik.tamkapp.R;

/**
 * Created by vili on 13/04/2017.
 */

public class MenuHeaderViewHolder extends MenuListViewHolder {
    TextView primaryTextView;

    public MenuHeaderViewHolder(View itemView) {
        super(itemView);

        primaryTextView = (TextView)itemView.findViewById(R.id.menu_header_text);
    }

    @Override
    TextView getPrimaryTextView() {
        return primaryTextView;
    }

    @Override
    TextView getSecondaryTextView() {
        return null;
    }


}
