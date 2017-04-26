package in.vilik.tamkapp.timetable;

/**
 * Defines an interface for timetable update listeners.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public interface OnTimetableUpdatedListener {

    /**
     * Triggers when update was successful.
     */
    void onSuccess();

    /**
     * Triggers when update was unsuccessful.
     */
    void onError();
}
