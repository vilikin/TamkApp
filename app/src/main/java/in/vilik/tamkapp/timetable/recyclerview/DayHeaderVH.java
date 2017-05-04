package in.vilik.tamkapp.timetable.recyclerview;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.bottomsheet.BottomSheetMenu;
import in.vilik.tamkapp.bottomsheet.Category;
import in.vilik.tamkapp.timetable.Day;
import in.vilik.tamkapp.timetable.TimetableElement;
import in.vilik.tamkapp.utils.DateUtil;

/**
 * Implements ViewHolder for day header.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
class DayHeaderVH extends TimetableViewHolder {

    /**
     * Initializes view holder.
     *
     * @param itemView  View of the ViewHolder
     */
    public DayHeaderVH(View itemView) {
        super(itemView);
    }

    /**
     * Binds day element to this view holder.
     *
     * Fills text elements with data from the day object.
     *
     * @param element   Timetable element representing the day header
     */
    @Override
    public void bind(TimetableElement element) {
        final Day day = (Day) element;
        TextView title = (TextView) itemView.findViewById(R.id.dayHeaderTitle);
        title.setText(day.getFormattedDate(DateUtil.DateFormat.ON_DAY));

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final BottomSheetMenu optionsDialog = new BottomSheetMenu(context);

                List<Category> categories = optionsDialog.getCategoriesForDate(day);

                optionsDialog.setCategories(categories);

                optionsDialog.show();

                return true;
            }
        });
    }
}
