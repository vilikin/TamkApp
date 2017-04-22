package in.vilik.tamkapp.timetable.recyclerview;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.NowBlock;
import in.vilik.tamkapp.timetable.Reservation;
import in.vilik.tamkapp.timetable.TimetableElement;
import in.vilik.tamkapp.timetable.deadlines.Deadline;
import in.vilik.tamkapp.timetable.deadlines.DeadlineStorage;
import in.vilik.tamkapp.utils.DataLoader;

/**
 * Created by vili on 16/04/2017.
 */

class DeadlineVH extends TimetableViewHolder {
    public DeadlineVH(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TimetableElement element) {
        final Deadline deadline = (Deadline) element;

        TextView title = (TextView) itemView.findViewById(R.id.deadline_title);

        title.setText(deadline.getName());

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DeadlineStorage.removeDeadline(itemView.getContext(), deadline);

                if (deadline.getTimetable() != null) {
                    deadline.getTimetable().loadData(DataLoader.LoadingStrategy.CACHE_FIRST);
                }

                return true;
            }
        });
    }
}
