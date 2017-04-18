package in.vilik.tamkapp.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.utils.DateUtil;

/**
 * Created by vili on 16/04/2017.
 */

public class Day implements TimetableElement {
    private Timetable parent;
    private Date date;
    private List<Reservation> reservations;

    public Day(Timetable parent) {
        this.parent = parent;
        this.reservations = new ArrayList<>();
    }

    public Timetable getParent() {
        return parent;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        return DateUtil.formatDate(parent.getContext(), date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public Type getType() {
        return Type.DAY_HEADER;
    }
}
