package in.vilik.tamkapp.timetable.recyclerview;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.Reservation;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Created by vili on 16/04/2017.
 */

class ReservationVH extends TimetableViewHolder {
    public ReservationVH(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TimetableElement element) {
        Reservation reservation = (Reservation) element;
        TextView title = (TextView) itemView.findViewById(R.id.reservationTitle);
        TextView realizations = (TextView) itemView.findViewById(R.id.reservationRealizationCodes);
        TextView date = (TextView) itemView.findViewById(R.id.reservationDate);
        TextView classRoom = (TextView) itemView.findViewById(R.id.reservationClassRoom);
        LinearLayout groupsLayout = (LinearLayout) itemView
                .findViewById(R.id.reservationStudentGroupsLayout);

        TextView groupsText = (TextView) itemView.findViewById(R.id.reservationStudentGroupsText);

        title.setText(reservation.getViewHeader());

        if (reservation.getRealizations().isEmpty()) {
            realizations.setVisibility(View.GONE);
        } else {
            realizations.setText(reservation.getRealizationsString());
        }

        date.setText(reservation.getViewDateString());

        if (reservation.getClassRoom() != null) {
            classRoom.setText(reservation.getClassRoom().getCode());
        } else {
            classRoom.setText(R.string.timetable_no_classroom);
        }

        List<String> groups = reservation.getStudentGroups();

        if (groups.size() <= 1) {
            groupsLayout.setVisibility(View.GONE);
        } else if (groups.size() <= 3){
            groupsText.setText(reservation.getStudentGroupsString());
        } else {
            String text = itemView.getContext().getResources()
                    .getString(R.string.timetable_more_groups,
                            groups.get(0), groups.size() - 1);

            groupsText.setText(text);
        }
    }
}
