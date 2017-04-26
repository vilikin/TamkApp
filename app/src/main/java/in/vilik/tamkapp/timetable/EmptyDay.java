package in.vilik.tamkapp.timetable;

import android.content.Context;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.utils.DateUtil;

/**
 * Implements day component for timetable that does not have any reservations.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class EmptyDay extends Day {

    /**
     * Initializes component based on an existing day instance.
     *
     * @param day   Day that has no reservations
     */
    public EmptyDay(Day day) {
        super(day.getParent());

        setDate(day.getDate());
    }

    /**
     * Gets literal format of the day with a note that there are no lectures.
     *
     * @param format    Format to be used by the DateUtil
     * @return          Formatted date
     */
    @Override
    public String getFormattedDate(DateUtil.DateFormat format) {
        Context context = getParent().getContext();

        String formatted = DateUtil.formatDate(context, getDate(), format);

        return context
                .getResources()
                .getString(R.string.timetable_no_reservations, formatted);
    }

    /**
     * Gets type of the timetable element.
     *
     * This information is used by recycler view of the timetable
     * to choose correct ViewHolder for the item.
     *
     * @return  Type.DAY_HEADER_EMPTY
     */
    @Override
    public Type getType() {
        return Type.DAY_HEADER_EMPTY;
    }
}
