package in.vilik.tamkapp.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.timetable.notes.Note;
import in.vilik.tamkapp.utils.DateUtil;

/**
 * Created by vili on 16/04/2017.
 */

public class Day implements TimetableElement {
    private Timetable parent;
    private Date date;
    private List<Reservation> reservations;
    private List<Note> notes;

    public Day(Timetable parent) {
        this.parent = parent;
        this.reservations = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    public Timetable getParent() {
        return parent;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate(DateUtil.DateFormat format) {
        return DateUtil.formatDate(parent.getContext(), date, format);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Gets type of the timetable element.
     *
     * This information is used by recycler view of the timetable
     * to choose correct ViewHolder for the item.
     *
     * @return  Type.DAY_HEADER
     */
    @Override
    public Type getType() {
        return Type.DAY_HEADER;
    }
}
