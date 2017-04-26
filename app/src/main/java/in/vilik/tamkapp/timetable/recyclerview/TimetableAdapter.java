package in.vilik.tamkapp.timetable.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.Timetable;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Implements adapter for RecyclerView that holds the timetable.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class TimetableAdapter extends RecyclerView.Adapter<TimetableViewHolder>{

    /**
     * Timetable instance that is rendered by the RecyclerView.
     */
    private Timetable timetable;

    /**
     * Initializes adapter with the timetable instance.
     *
     * @param timetable Timetable instance
     */
    public TimetableAdapter(Timetable timetable) {
        this.timetable = timetable;
    }

    /**
     * Returns type for a view in specified position.
     *
     * @param position  Position of the view
     * @return          Type for the view in specified position
     */
    @Override
    public int getItemViewType(int position) {
        return timetable.getElements().get(position).getType().ordinal();
    }

    /**
     * Creates correct view holder specific view type.
     *
     * @param parent    Parent ViewGroup
     * @param viewType  Type of the view
     * @return          ViewHolder for specified view type
     */
    @Override
    public TimetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        TimetableElement.Type type = TimetableElement.Type.values()[viewType];

        switch (type) {
            case ANNOUNCEMENT_BLOCK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_announcement_block, parent, false);
                return new AnnouncementBlockVH(view);
            case NOW_BLOCK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_now_block, parent, false);
                return new NowBlockVH(view);
            case DAY_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_day_header, parent, false);
                return new DayHeaderVH(view);
            case DAY_HEADER_EMPTY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_day_header_empty, parent, false);
                return new DayHeaderVH(view);
            case RESERVATION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_reservation, parent, false);
                return new ReservationVH(view);
            case NOTE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_note, parent, false);
                return new NoteVH(view);
        }

        return null;
    }

    /**
     * Binds data to a view holder in specified position.
     *
     * @param holder    ViewHolder to bind data to
     * @param position  Position of the ViewHolder
     */
    @Override
    public void onBindViewHolder(TimetableViewHolder holder, int position) {
        holder.bind(timetable.getElements().get(position));
    }

    /**
     * Gets total number of items in the timetable.
     *
     * @return  Total number of items in the timetable
     */
    @Override
    public int getItemCount() {
        return timetable.getElements().size();
    }
}
