package in.vilik.tamkapp.timetable.recyclerview;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.bottomsheet.BottomSheetOptions;
import in.vilik.tamkapp.bottomsheet.Category;
import in.vilik.tamkapp.timetable.Day;
import in.vilik.tamkapp.timetable.TimetableElement;
import in.vilik.tamkapp.utils.DateUtil;

/**
 * Created by vili on 16/04/2017.
 */

class DayHeaderVH extends TimetableViewHolder {
    public DayHeaderVH(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TimetableElement element) {
        final Day day = (Day) element;
        TextView title = (TextView) itemView.findViewById(R.id.dayHeaderTitle);
        title.setText(day.getFormattedDate(DateUtil.DateFormat.ON_DAY));

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final BottomSheetOptions optionsDialog = new BottomSheetOptions(context);

                List<Category> categories = optionsDialog.getCategoriesForDate(day);

                optionsDialog.setCategories(categories);

                optionsDialog.show();

                return true;
            }
        });
    }
}
