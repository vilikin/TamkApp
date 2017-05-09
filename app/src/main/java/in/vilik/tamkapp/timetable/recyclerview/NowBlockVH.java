package in.vilik.tamkapp.timetable.recyclerview;

import android.view.View;
import android.widget.TextView;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.NowBlock;
import in.vilik.tamkapp.timetable.Reservation;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Implements ViewHolder for now block.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
class NowBlockVH extends TimetableViewHolder {

    /**
     * Initializes view holder.
     *
     * @param itemView  View of the ViewHolder
     */
    public NowBlockVH(View itemView) {
        super(itemView);
    }

    /**
     * Binds now block to this view holder.
     *
     * Fills text elements with data from the now block object.
     *
     * @param element   Timetable element representing now block item
     */
    @Override
    public void bind(TimetableElement element) {
        NowBlock nowBlock = (NowBlock) element;
        Reservation reservation = nowBlock.getReservation();

        TextView title = (TextView) itemView.findViewById(R.id.nowBlockTitle);
        TextView classRoom = (TextView) itemView.findViewById(R.id.nowBlockClassRoom);
        TextView date = (TextView) itemView.findViewById(R.id.nowBlockDate);

        title.setText(reservation.getViewHeader());
        date.setText(reservation.getViewDateString());

        if (reservation.getClassRoom() != null) {
            classRoom.setText(reservation.getClassRoom().getCode());
        } else {
            classRoom.setText(R.string.timetable_no_classroom);
        }
    }
}
