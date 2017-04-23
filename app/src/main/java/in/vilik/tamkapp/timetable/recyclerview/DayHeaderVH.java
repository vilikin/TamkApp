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
        title.setText(day.getFormattedDate());

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final BottomSheetOptions optionsDialog = new BottomSheetOptions(context);

                List<Category> categories = new ArrayList<>();

                Category dayCategory = new Category();
                dayCategory.setName(day.getFormattedDate());
                List<Option> options = new ArrayList<>();
                Option deadline = new Option();

                deadline.setName(context.getString(R.string.deadline_activity_title));
                deadline.setIcon(getDrawable(R.drawable.ic_date_black_24dp));
                deadline.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewNote(day.getDate(), Note.NoteType.DEADLINE);
                        optionsDialog.dismiss();
                    }
                });

                options.add(deadline);

                Option exam = new Option();

                exam.setName(context.getString(R.string.exam_activity_title));
                exam.setIcon(getDrawable(R.drawable.ic_group_black_24dp));
                exam.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewNote(day.getDate(), Note.NoteType.EXAM);
                        optionsDialog.dismiss();
                    }
                });

                options.add(exam);

                Option event = new Option();

                event.setName(context.getString(R.string.event_activity_title));
                event.setIcon(getDrawable(R.drawable.ic_date_black_24dp));
                event.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewNote(day.getDate(), Note.NoteType.EVENT);
                        optionsDialog.dismiss();
                    }
                });

                options.add(event);

                Option note = new Option();

                note.setName(context.getString(R.string.note_activity_title));
                note.setIcon(getDrawable(R.drawable.ic_date_black_24dp));
                note.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewNote(day.getDate(), Note.NoteType.NOTE);
                        optionsDialog.dismiss();
                    }
                });

                options.add(note);

                dayCategory.setOptions(options);

                categories.add(dayCategory);

                optionsDialog.setCategories(categories);

                optionsDialog.show();

                return true;
            }
        });
    }

    private void createNewNote(Date date, Note.NoteType type) {
        Intent intent = new Intent(context, NoteActivity.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        intent.putExtra("calendar", calendar);
        intent.putExtra("fullDay", true);
        intent.putExtra("type", type.ordinal());

        context.startActivity(intent);
    }

    private Drawable getDrawable(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id, null);
        }
    }
}
