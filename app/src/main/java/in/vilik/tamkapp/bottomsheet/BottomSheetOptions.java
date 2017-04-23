package in.vilik.tamkapp.bottomsheet;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.vilik.tamkapp.NoteActivity;
import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.Campusravita;
import in.vilik.tamkapp.timetable.Day;
import in.vilik.tamkapp.timetable.notes.Note;
import in.vilik.tamkapp.utils.DateUtil;
import in.vilik.tamkapp.utils.UtilCompat;

/**
 * Created by vili on 23/04/2017.
 */

public class BottomSheetOptions extends BottomSheetDialog {
    private Context context;
    private LinearLayout rootView;

    public BottomSheetOptions(@NonNull Context context) {
        super(context);

        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);

        rootView = (LinearLayout) inflater.inflate(R.layout.bottom_sheet_dialog, null);

        setContentView(rootView);
    }

    public List<Category> getCategoriesForDate(final Day day) {
        List<Category> categories = new ArrayList<>();

        Category dayCategory = new Category();
        dayCategory.setName(DateUtil.formatDate(context, day.getDate(), DateUtil.DateFormat.DAY));
        List<Option> options = new ArrayList<>();
        Option deadline = new Option();

        deadline.setName(context.getString(R.string.deadline_bottom_sheet_title));
        deadline.setIcon(UtilCompat.getDrawable(context, R.drawable.ic_whatshot_black_24px));
        deadline.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNote(day.getDate(), Note.NoteType.DEADLINE);
                BottomSheetOptions.this.dismiss();
            }
        });

        options.add(deadline);

        Option exam = new Option();

        exam.setName(context.getString(R.string.exam_bottom_sheet_title));
        exam.setIcon(UtilCompat.getDrawable(context, R.drawable.ic_school_black_24px));
        exam.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNote(day.getDate(), Note.NoteType.EXAM);
                BottomSheetOptions.this.dismiss();
            }
        });

        options.add(exam);

        Option event = new Option();

        event.setName(context.getString(R.string.event_bottom_sheet_title));
        event.setIcon(UtilCompat.getDrawable(context, R.drawable.ic_event_available_black_24px));
        event.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNote(day.getDate(), Note.NoteType.EVENT);
                BottomSheetOptions.this.dismiss();
            }
        });

        options.add(event);

        Option note = new Option();

        note.setName(context.getString(R.string.note_bottom_sheet_title));
        note.setIcon(UtilCompat.getDrawable(context, R.drawable.ic_toc_black_24px));
        note.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNote(day.getDate(), Note.NoteType.NOTE);
                BottomSheetOptions.this.dismiss();
            }
        });

        options.add(note);

        dayCategory.setOptions(options);

        categories.add(dayCategory);

        return categories;
    }

    private void createNewNote(Date date, Note.NoteType type) {
        Intent intent = new Intent(context, NoteActivity.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        intent.putExtra("date", calendar);
        intent.putExtra("fullDay", true);
        intent.putExtra("type", type.ordinal());

        context.startActivity(intent);
    }

    public void setCategories(List<Category> categories) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (Category category : categories) {
            View categoryView = inflater.inflate(R.layout.bottom_sheet_dialog_category, null);

            TextView categoryText = (TextView) categoryView.findViewById(R.id.item_header);

            categoryText.setText(category.getName());

            rootView.addView(categoryView);

            for (Option option : category.getOptions()) {
                View optionView = inflater.inflate(R.layout.bottom_sheet_dialog_item, null);
                ImageView icon = (ImageView) optionView.findViewById(R.id.item_icon);

                icon.setImageDrawable(option.getIcon());

                TextView text = (TextView) optionView.findViewById(R.id.item_text);

                text.setText(option.getName());

                optionView.setOnClickListener(option.getListener());

                rootView.addView(optionView);
            }
        }
    }
}
