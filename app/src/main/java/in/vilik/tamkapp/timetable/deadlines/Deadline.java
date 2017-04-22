package in.vilik.tamkapp.timetable.deadlines;

import java.util.Date;

import in.vilik.tamkapp.timetable.Timetable;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Created by vili on 22/04/2017.
 */

public class Deadline implements TimetableElement {
    private Date date;
    private boolean fullDay;
    private String name;

    private Timetable timetable;

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
        return Type.DEADLINE;
    }
}
