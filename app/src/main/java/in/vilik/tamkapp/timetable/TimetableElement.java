package in.vilik.tamkapp.timetable;

/**
 * Implements interface for all timetable UI components.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public interface TimetableElement {
    /**
     * Defines available types for timetable elements.
     */
    enum Type {
        NOW_BLOCK, DAY_HEADER, DAY_HEADER_EMPTY, RESERVATION, ANNOUNCEMENT_BLOCK, NOTE
    }

    /**
     * Gets type of the timetable element.
     *
     * This information is used by recycler view of the timetable
     * to choose correct ViewHolder for the item.
     *
     * @return  Type of the timetable element
     */
    Type getType();
}
