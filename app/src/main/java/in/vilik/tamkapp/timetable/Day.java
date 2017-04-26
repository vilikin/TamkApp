package in.vilik.tamkapp.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.timetable.notes.Note;
import in.vilik.tamkapp.utils.DateUtil;

/**
 * Implements day component for timetable.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class Day implements TimetableElement {

    /**
     * Parent timetable the day is contained in.
     */
    private Timetable parent;

    /**
     * Date object of the day.
     */
    private Date date;

    /**
     * List of reservations associated with the day.
     */
    private List<Reservation> reservations;

    /**
     * List of notes associated with the day.
     */
    private List<Note> notes;

    /**
     * Initializes day with empty lists of reservations and notes.
     *
     * @param parent Parent timetable the day is contained in.
     */
    public Day(Timetable parent) {
        this.parent = parent;
        this.reservations = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    /**
     * Gets parent timetable of the day.
     *
     * @return Parent timetable of the day
     */
    public Timetable getParent() {
        return parent;
    }

    /**
     * Gets Date object of the day.
     *
     * @return  Date object of the day
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets literal format of the day.
     *
     * @param format    Format to be used by the DateUtil
     * @return          Formatted date
     */
    public String getFormattedDate(DateUtil.DateFormat format) {
        return DateUtil.formatDate(parent.getContext(), date, format);
    }

    /**
     * Sets Date object for the day.
     *
     * @param date  Date object for the day
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets list of reservations associated with the day.
     *
     * @return  List of reservation associated with the day
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Gets list of notes associated with the day.
     *
     * @return List of notes associated with the day
     */
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
