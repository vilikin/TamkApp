package in.vilik.tamkapp.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vili on 16/04/2017.
 */

public class Reservation implements TimetableElement {
    private Day parent;
    private String subject;
    private String description;
    private Date startDate;
    private Date endDate;

    private ClassRoom classRoom;
    private List<Realization> realizations;
    private List<String> studentGroups;

    public Reservation(Day parent) {
        this.parent = parent;
        this.realizations = new ArrayList<>();
        this.studentGroups = new ArrayList<>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public List<Realization> getRealizations() {
        return realizations;
    }

    public void setRealizations(List<Realization> realizations) {
        this.realizations = realizations;
    }

    public List<String> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<String> studentGroups) {
        this.studentGroups = studentGroups;
    }

    @Override
    public Type getType() {
        return Type.RESERVATION;
    }
}
