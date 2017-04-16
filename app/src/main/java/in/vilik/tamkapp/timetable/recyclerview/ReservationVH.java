package in.vilik.tamkapp.timetable.recyclerview;

import android.view.View;
import android.widget.TextView;

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
        title.setText(reservation.getSubject());
    }
}
