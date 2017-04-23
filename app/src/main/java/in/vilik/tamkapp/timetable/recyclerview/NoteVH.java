package in.vilik.tamkapp.timetable.recyclerview;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.TimetableElement;
import in.vilik.tamkapp.timetable.notes.Note;
import in.vilik.tamkapp.timetable.notes.NoteStorage;
import in.vilik.tamkapp.utils.DataLoader;
import in.vilik.tamkapp.utils.DateUtil;
import in.vilik.tamkapp.utils.UtilCompat;

/**
 * Created by vili on 16/04/2017.
 */

class NoteVH extends TimetableViewHolder {
    public NoteVH(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TimetableElement element) {
        final Note note = (Note) element;

        TextView title = (TextView) itemView.findViewById(R.id.note_title);
        ImageView icon = (ImageView) itemView.findViewById(R.id.note_icon);

        switch (note.getNoteType()) {
            case DEADLINE:
                icon.setImageDrawable(UtilCompat.getDrawable(context, R.drawable.ic_whatshot_black_24px));
                break;
            case EXAM:
                icon.setImageDrawable(UtilCompat.getDrawable(context, R.drawable.ic_school_black_24px));
                break;
            case EVENT:
                icon.setImageDrawable(UtilCompat.getDrawable(context, R.drawable.ic_event_available_black_24px));
                break;
            case NOTE:
                icon.setImageDrawable(UtilCompat.getDrawable(context, R.drawable.ic_toc_black_24px));
                break;
        }

        String text = note.getName();

        if (!note.isFullDay()) {
            text = DateUtil.getDigitalTime(note.getDate()) + " " + text;
        }

        title.setText(text);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.note_remove_confirm_title)
                        .setMessage(context.getString(R.string.note_remove_confirm_message, note.getName()))
                        .setPositiveButton(R.string.note_remove_confirm_button,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        NoteStorage.removeNote(itemView.getContext(), note);

                                        if (note.getTimetable() != null) {
                                            note.getTimetable().loadData(DataLoader.LoadingStrategy.CACHE_FIRST);
                                        }
                                    }
                                })
                        .setNegativeButton(R.string.note_remove_cancel_button, null)
                        .show();

                return true;
            }
        });
    }
}
