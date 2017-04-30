package in.vilik.tamkapp.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.utils.DateUtil;

/**
 * Implements a reservation component for timetable.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class Reservation implements TimetableElement {

    /**
     * ID of the reservation.
     */
    private int id;

    /**
     * Day component that holds the reservation.
     */
    private Day parent;

    /**
     * Subject of the reservation.
     */
    private String subject;

    /**
     * Description of the reservation.
     */
    private String description;

    /**
     * Start date of the reservation.
     */
    private Date startDate;

    /**
     * End date of the reservation.
     */
    private Date endDate;

    /**
     * Class room of the reservation.
     */
    private ClassRoom classRoom;

    /**
     * List of realizations.
     */
    private List<Realization> realizations;

    /**
     * List of student groups associated with this reservation.
     */
    private List<String> studentGroups;

    /**
     * Initializes reservation with empty lists of realizations and student groups.
     */
    public Reservation() {
        this.realizations = new ArrayList<>();
        this.studentGroups = new ArrayList<>();
    }

    /**
     * Gets id of the reservation.
     *
     * @return Id of the reservation
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id for the reservation.
     *
     * @param id Id for the reservation
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets parent timetable of the reservation.
     *
     * @return  Parent timetable of the reservation
     */
    public Day getParent() {
        return parent;
    }

    /**
     * Sets parent timetable for the reservation.
     *
     * @param parent Sets parent timetable for the reservation
     */
    public void setParent(Day parent) {
        this.parent = parent;
    }

    /**
     * Gets subject of the reservation.
     *
     * @return Subject of the reservation
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject for the reservation.
     *
     * @param subject Subject for the reservation
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets description of the reservation.
     *
     * @return Description of the reservation
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description for the reservation.
     *
     * @param description Description for the reservation
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets start date of the reservation.
     *
     * @return Start date of the reservation
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets start date for the reservation.
     *
     * @param startDate Start date for the reservation
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date of the reservation.
     *
     * @return End date of the reservation
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets end date for the reservation.
     *
     * @param endDate End date for the reservation
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets class room of the reservation.
     *
     * @return Class room for the reservation
     */
    public ClassRoom getClassRoom() {
        return classRoom;
    }

    /**
     * Sets class room for the reservation.
     *
     * @param classRoom Class room for the reservation
     */
    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    /**
     * Gets realizations associated with the reservation.
     *
     * @return Realizations associated with the reservation
     */
    public List<Realization> getRealizations() {
        return realizations;
    }

    /**
     * Sets realization associated with the reservation.
     *
     * @param realizations Realizations associated with the reservation
     */
    public void setRealizations(List<Realization> realizations) {
        this.realizations = realizations;
    }

    /**
     * Gets student groups associated with the reservation.
     *
     * @return Student groups associated with the reservation
     */
    public List<String> getStudentGroups() {
        return studentGroups;
    }

    /**
     * Sets student groups associated with the reservation.
     *
     * @param studentGroups Student groups associated with the reservation
     */
    public void setStudentGroups(List<String> studentGroups) {
        this.studentGroups = studentGroups;
    }

    /**
     * Constructs header of the reservation for the Recycler View.
     *
     * @return  Header of the reservation
     */
    public String getViewHeader() {
        if (subjectHasRealizationString()) {
            StringBuilder sb = new StringBuilder();

            for (Realization realization : realizations) {
                if (!sb.toString().contains(realization.getName())) {
                    sb.append("\n").append(realization.getName());
                }
            }

            return sb.toString().substring(1);
        } else {
            return subject;
        }
    }

    /**
     * Concatenates all realization codes associated with the reservation into one string.
     *
     * @return All realization codes associated with the reservation comma separated
     */
    public String getRealizationsString() {
        StringBuilder sb = new StringBuilder();

        for (Realization realization : realizations) {
            sb.append(", ").append(realization.getCode());
        }

        return sb.toString().substring(2);
    }

    /**
     * Concatenates all student groups associated with the reservation into one string.
     *
     * @return All student groups associated with the reservation comma separated
     */
    public String getStudentGroupsString() {
        StringBuilder sb = new StringBuilder();

        for (String studentGroup : studentGroups) {
            sb.append(", ").append(studentGroup);
        }

        return sb.toString().substring(2);
    }

    /**
     * Constructs time period string for the Recycler View.
     *
     * @return  Time period string
     */
    public String getViewDateString() {
        String start = DateUtil.getDigitalTime(startDate);
        String end = DateUtil.getDigitalTime(endDate);

        return start + " - " + end;
    }

    /**
     * Checks if subject of the reservation contains realization string.
     *
     * @return  If subject contains realization string or not
     */
    private boolean subjectHasRealizationString() {
        if (realizations.size() == 0) {
            return false;
        } else {
            int realizationsFound = 0;

            for (Realization realization : realizations) {
                if (subject.contains(realization.getCode())) {
                    realizationsFound++;
                }
            }

            return realizationsFound == realizations.size();
        }
    }

    /**
     * Gets type of the timetable element.
     *
     * This information is used by recycler view of the timetable
     * to choose correct ViewHolder for the item.
     *
     * @return  Type.RESERVATION
     */
    @Override
    public Type getType() {
        return Type.RESERVATION;
    }
}
