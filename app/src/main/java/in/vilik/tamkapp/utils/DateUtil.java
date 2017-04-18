package in.vilik.tamkapp.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.Debug;

import static java.util.Calendar.DATE;

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

    public static List<Date> getThisWeek() {
        List<Date> days = new ArrayList<>();

        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        days.add(today);

        for (int i = 0; i < 7; i++) {
            calendar.add(DATE, 1);

            if (calendar.get(Calendar.WEEK_OF_YEAR) != weekOfYear) {
                break;
            }

            days.add(calendar.getTime());
        }

        return days;
    }

    public static List<Date> getDays(int amount) {
        List<Date> days = new ArrayList<>();

        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        days.add(today);

        for (int i = 0; i < amount - 1; i++) {
            calendar.add(DATE, 1);
            days.add(calendar.getTime());
        }

        Debug.log("getDays()", "Was asked for " + amount + ", i decided to give " + days.size());

        return days;
    }
}
