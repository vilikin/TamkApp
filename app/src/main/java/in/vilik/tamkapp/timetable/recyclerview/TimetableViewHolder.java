package in.vilik.tamkapp.timetable.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Implements base ViewHolder for timetable items.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
abstract class TimetableViewHolder extends RecyclerView.ViewHolder {
    /**
     * Context.
     */
    Context context;

    /**
     * View of the ViewHolder.
     */
    View itemView;

    /**
     * Initializes view holder.
     *
     * @param itemView  View of the ViewHolder
     */
    public TimetableViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        this.context = itemView.getContext();
    }

    /**
     * Binds timetable element to this view holder.
     *
     * Fills text elements with data from the timetable element.
     *
     * @param element   Timetable element to bind
     */
    abstract public void bind(TimetableElement element);
}
