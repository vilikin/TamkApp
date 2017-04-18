package in.vilik.tamkapp.timetable;

import android.content.Context;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.utils.DateUtil;

/**
 * Created by vili on 18/04/2017.
 */

public class EmptyDay extends Day {
    public EmptyDay(Day day) {
        super(day.getParent());

        setDate(day.getDate());
    }

    @Override
    public String getFormattedDate() {
        Context context = getParent().getContext();

        String formatted = DateUtil.formatDate(context, getDate());

        return context
                .getResources()
                .getString(R.string.timetable_no_reservations, formatted);
    }

    @Override
    public Type getType() {
        return Type.DAY_HEADER_EMPTY;
    }
}
