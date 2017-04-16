package in.vilik.tamkapp.timetable.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Created by vili on 16/04/2017.
 */

abstract class TimetableViewHolder extends RecyclerView.ViewHolder {
    View itemView;

    public TimetableViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
    }

    abstract public void bind(TimetableElement element);
}
