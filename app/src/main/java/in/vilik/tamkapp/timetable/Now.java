package in.vilik.tamkapp.timetable;

/**
 * Created by vili on 16/04/2017.
 */

public class Now extends Reservation implements TimetableElement {
    private Timetable parent;

    public Now(Timetable parent) {
        super(null);
        this.parent = parent;
    }

    @Override
    public Type getType() {
        return null;
    }
}
