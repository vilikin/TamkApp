package in.vilik.tamkapp.timetable.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.NoteActivity;
import in.vilik.tamkapp.R;
import in.vilik.tamkapp.bottomsheet.BottomSheetOptions;
import in.vilik.tamkapp.bottomsheet.Category;
import in.vilik.tamkapp.bottomsheet.Option;
import in.vilik.tamkapp.timetable.Day;
import in.vilik.tamkapp.timetable.TimetableElement;
import in.vilik.tamkapp.timetable.notes.Note;
import in.vilik.tamkapp.utils.DateUtil;

import static android.R.attr.type;

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
