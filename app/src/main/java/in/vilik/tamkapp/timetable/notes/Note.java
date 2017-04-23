package in.vilik.tamkapp.timetable.notes;

import java.util.Date;

import in.vilik.tamkapp.timetable.Timetable;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Created by vili on 22/04/2017.
 */

public class Note implements TimetableElement {
    public enum NoteType {
        DEADLINE, EXAM, EVENT, NOTE
    }

    private Date date;
    private boolean fullDay;
    private String name;
    private NoteType noteType;

    private Timetable timetable;

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFullDay() {
        return fullDay;
    }

    public void setFullDay(boolean fullDay) {
        this.fullDay = fullDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Type getType() {
        return Type.NOTE;
    }
}
