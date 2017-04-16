package in.vilik.tamkapp.timetable;

import java.util.Date;
import java.util.List;

/**
 * Created by vili on 16/04/2017.
 */

public class Day implements TimetableElement {
    private Timetable parent;
    private Date date;
    private List<Reservation> reservations;

    public Day(Timetable parent) {
        this.parent = parent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public Type getType() {
        return Type.DAY_HEADER;
    }
}
