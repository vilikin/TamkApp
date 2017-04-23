package in.vilik.tamkapp.utils;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.R;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_YEAR;

/**
 * Created by vili on 16/04/2017.
 */
public class DateUtil {
    public enum DateFormat {
        DAY, ON_DAY
    }

    public static boolean areOnSameDay(Date first, Date second) {
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.setTime(first);

        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.setTime(second);

        return firstCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR) &&
               firstCalendar.get(Calendar.DAY_OF_YEAR) == secondCalendar.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isOnRange(Date rangeStart, Date rangeEnd, Date date) {
        return date.after(rangeStart) && date.before(rangeEnd);
    }

    /*
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
    */

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

        return days;
    }

    public static String formatDate(Context context, Date date, DateFormat format) {
        Resources resources = context.getResources();

        Calendar now = Calendar.getInstance();

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(DATE, 1);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (format == DateFormat.ON_DAY && now.get(DAY_OF_YEAR) == calendar.get(DAY_OF_YEAR)) {
            return resources.getString(R.string.date_today);
        } else if (format == DateFormat.ON_DAY && tomorrow.get(DAY_OF_YEAR) == calendar.get(DAY_OF_YEAR)) {
            return resources.getString(R.string.date_tomorrow);
        } else {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);

            String[] weekdays;

            switch (format) {
                case DAY:
                    weekdays = resources.getStringArray(R.array.weekdays_normal);
                    break;
                case ON_DAY:
                    weekdays = resources.getStringArray(R.array.weekdays);
                    break;
                default:
                    weekdays = resources.getStringArray(R.array.weekdays_normal);
            }

            return weekdays[dayOfWeek - 1] + " " + dayOfMonth + "." + (month + 1);
        }
    }

    public static String getDigitalTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) +
                ":" +
                toTwoDigits(calendar.get(Calendar.MINUTE));
    }

    public static String toTwoDigits(int number) {
        if (number <= 9) {
            return "0" + number;
        } else {
            return String.valueOf(number);
        }
    }
}
