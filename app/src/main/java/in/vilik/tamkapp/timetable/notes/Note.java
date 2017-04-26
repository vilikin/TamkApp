package in.vilik.tamkapp.timetable.notes;

import java.util.Date;

import in.vilik.tamkapp.timetable.Timetable;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Implements a class representing timetable note object.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class Note implements TimetableElement {

    /**
     * Defines available types for notes.
     */
    public enum NoteType {
        DEADLINE, EXAM, EVENT, NOTE
    }

    /**
     * Date of the note.
     */
    private Date date;

    /**
     * Is the note for full day or specific time.
     */
    private boolean fullDay;

    /**
     * Name of the note.
     */
    private String name;

    /**
     * Type of the note.
     */
    private NoteType noteType;

    /**
     * Parent timetable instance of the note.
     */
    private Timetable timetable;

    /**
     * Sets type of the note.
     *
     * @param noteType  Type for the note
     */
    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    /**
     * Gets type of the note.
     *
     * @return  Type of the note
     */
    public NoteType getNoteType() {
        return noteType;
    }

    /**
     * Gets parent timetable instance of the note.
     *
     * @return Parent timetable instance of the note
     */
    public Timetable getTimetable() {
        return timetable;
    }

    /**
     * Sets parent timetable instance for the note
     *
     * @param timetable Parent timetable instance for the note
     */
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    /**
     * Gets date of the note.
     *
     * @return  Date of the note
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date for the note.
     *
     * @param date  Date for the note
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Checks if note is for full day or a specific time.
     *
     * @return  If note is for a full day or not
     */
    public boolean isFullDay() {
        return fullDay;
    }

    /**
     * Sets a flag for whether the note is for full day or specific time.
     *
     * @param fullDay   If note is for a full day or not
     */
    public void setFullDay(boolean fullDay) {
        this.fullDay = fullDay;
    }

    /**
     * Gets name of note.
     *
     * @return Name of the note
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for the note.
     *
     * @param name  Name for the note
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets type of the timetable element.
     *
     * This information is used by recycler view of the timetable
     * to choose correct ViewHolder for the item.
     *
     * @return  Type.NOTE
     */
    @Override
    public Type getType() {
        return Type.NOTE;
    }
}
