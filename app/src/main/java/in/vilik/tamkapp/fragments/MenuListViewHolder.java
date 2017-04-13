package in.vilik.tamkapp.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vili on 13/04/2017.
 */

public abstract class MenuListViewHolder extends RecyclerView.ViewHolder {
    public MenuListViewHolder(View itemView) {
        super(itemView);
    }

    abstract TextView getPrimaryTextView();
    abstract TextView getSecondaryTextView();
}
