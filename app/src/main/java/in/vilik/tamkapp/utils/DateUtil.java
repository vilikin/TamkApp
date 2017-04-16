package in.vilik.tamkapp.utils;

import java.util.Date;

/**
 * Created by vili on 16/04/2017.
 */

public class DateUtil {
    public static boolean areOnSameDay(Date first, Date second) {
        long firstDay = first.getTime() / (1000 * 60 * 60 * 24);
        long secondDay = second.getTime() / (1000 * 60 * 60 * 24);

        return firstDay == secondDay;
    }

    public static boolean isOnRange(Date rangeStart, Date rangeEnd, Date date) {
        return date.after(rangeStart) && date.before(rangeEnd);
    }
}
