package in.vilik.tamkapp.timetable;


/**
 * Created by vili on 16/04/2017.
 */

public class NowBlock implements TimetableElement {
    private Reservation reservation;

    public NowBlock(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * Gets type of the timetable element.
     *
     * This information is used by recycler view of the timetable
     * to choose correct ViewHolder for the item.
     *
     * @return  Type.NOW_BLOCK
     */
    @Override
    public Type getType() {
        return Type.NOW_BLOCK;
    }
}
