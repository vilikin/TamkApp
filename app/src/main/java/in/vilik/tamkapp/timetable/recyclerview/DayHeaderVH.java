package in.vilik.tamkapp.timetable.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.Day;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Created by vili on 16/04/2017.
 */

class DayHeaderVH extends TimetableViewHolder {
    public DayHeaderVH(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TimetableElement element) {
        Day day = (Day) element;
        TextView title = (TextView) itemView.findViewById(R.id.dayHeaderTitle);
        title.setText(day.getFormattedDate());
    }
}
