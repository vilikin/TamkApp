package in.vilik.tamkapp.timetable.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.Timetable;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Created by vili on 16/04/2017.
 */
public class TimetableAdapter extends RecyclerView.Adapter<TimetableViewHolder>{
    private Timetable timetable;

    public TimetableAdapter(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public int getItemViewType(int position) {
        return timetable.getElements().get(position).getType().ordinal();
    }

    @Override
    public TimetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        TimetableElement.Type type = TimetableElement.Type.values()[viewType];

        switch (type) {
            case NOW_BLOCK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_now_block, parent, false);
                return new NowBlockVH(view);
            case DAY_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_day_header, parent, false);
                return new DayHeaderVH(view);
            case RESERVATION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_reservation, parent, false);
                return new ReservationVH(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(TimetableViewHolder holder, int position) {
        holder.bind(timetable.getElements().get(position));
    }

    @Override
    public int getItemCount() {
        return timetable.getElements().size();
    }
}
