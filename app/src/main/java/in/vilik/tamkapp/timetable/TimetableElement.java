package in.vilik.tamkapp.timetable;

/**
 * Created by vili on 16/04/2017.
 */

public interface TimetableElement {
    enum Type {
        NOW_BLOCK, DAY_HEADER, RESERVATION
    }

    Type getType();
}
