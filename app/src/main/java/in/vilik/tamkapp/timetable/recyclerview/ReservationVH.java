package in.vilik.tamkapp.timetable.recyclerview;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.bottomsheet.BottomSheetMenu;
import in.vilik.tamkapp.bottomsheet.Category;
import in.vilik.tamkapp.timetable.Reservation;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Implements ViewHolder for reservation.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
class ReservationVH extends TimetableViewHolder {

    /**
     * Initializes view holder.
     *
     * @param itemView  View of the ViewHolder
     */
    public ReservationVH(View itemView) {
        super(itemView);
    }

    /**
     * Binds reservation to this view holder.
     *
     * Fills text elements with data from the reservation object.
     *
     * @param element   Timetable element representing the reservation item
     */
    @Override
    public void bind(TimetableElement element) {
        final Reservation reservation = (Reservation) element;
        TextView title = (TextView) itemView.findViewById(R.id.reservationTitle);
        TextView realizations = (TextView) itemView.findViewById(R.id.reservationRealizationCodes);
        TextView date = (TextView) itemView.findViewById(R.id.reservationDate);
        TextView classRoom = (TextView) itemView.findViewById(R.id.reservationClassRoom);
        LinearLayout groupsLayout = (LinearLayout) itemView
                .findViewById(R.id.reservationStudentGroupsLayout);

        final TextView groupsText = (TextView) itemView.findViewById(R.id.reservationStudentGroupsText);

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

        final List<String> groups = reservation.getStudentGroups();

        if (groups.size() <= 1) {
            groupsLayout.setVisibility(View.GONE);
        } else if (groups.size() <= 3){
            groupsLayout.setVisibility(View.VISIBLE);
            groupsText.setText(reservation.getStudentGroupsString());
        } else {
            groupsLayout.setVisibility(View.VISIBLE);
            String text = itemView.getContext().getResources()
                    .getString(R.string.timetable_more_groups,
                            groups.get(0), groups.size() - 1);

            groupsText.setText(text);
        }

        if (groups.size() > 3) {
            groupsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!groupsText.getText().equals(reservation.getStudentGroupsString())) {
                        groupsText.setText(reservation.getStudentGroupsString());
                    } else {
                        groupsText.setText(itemView.getContext().getResources()
                                .getString(R.string.timetable_more_groups,
                                        groups.get(0), groups.size() - 1));
                    }
                }
            });
        }

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final BottomSheetMenu optionsDialog = new BottomSheetMenu(context);

                List<Category> categories = optionsDialog.getCategoriesForDate(reservation.getParent());
                optionsDialog.setCategories(categories);

                optionsDialog.show();

                return true;
            }
        });
    }
}
