package in.vilik.tamkapp.timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.utils.DateUtil;

/**
 * Created by vili on 16/04/2017.
 */

public class Reservation implements TimetableElement {
    private int id;
    private Day parent;
    private String subject;
    private String description;
    private Date startDate;
    private Date endDate;

    private ClassRoom classRoom;
    private List<Realization> realizations;
    private List<String> studentGroups;

    public Reservation() {
        this.realizations = new ArrayList<>();
        this.studentGroups = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Day getParent() {
        return parent;
    }

    public void setParent(Day parent) {
        this.parent = parent;
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

    public String getViewHeader() {
        if (subjectHasRealizationString()) {
            StringBuilder sb = new StringBuilder();

            for (Realization realization : realizations) {
                sb.append("\n").append(realization.getName());
            }

            return sb.toString().substring(1);
        } else {
            return subject;
        }
    }

    public String getViewDateString() {
        String start = DateUtil.getDigitalTime(startDate);
        String end = DateUtil.getDigitalTime(endDate);

        return start + " - " + end;
    }

    private boolean subjectHasRealizationString() {
        if (realizations.size() == 0) {
            return false;
        } else {
            int realizationsFound = 0;

            for (Realization realization : realizations) {
                if (subject.contains(realization.getName() + " " + realization.getCode())) {
                    realizationsFound++;
                }
            }

            return realizationsFound == realizations.size();
        }
    }
}
