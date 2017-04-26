package in.vilik.tamkapp.timetable;

/**
 * Implements now block component for timetable.
 *
 * Now block contains one reservation that is currently
 * active and displays it highlighted over everything else.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class NowBlock implements TimetableElement {

    /**
     * Reservation displayed on the now block.
     */
    private Reservation reservation;

    /**
     * Initializes now block with a reservation.
     *
     * @param reservation   Reservation to display in the now block
     */
    public NowBlock(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * Gets reservation of the now block.
     *
     * @return Reservation of the now block
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Sets reservation for the now block.
     *
     * @param reservation Reservation for the now block
     */
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
