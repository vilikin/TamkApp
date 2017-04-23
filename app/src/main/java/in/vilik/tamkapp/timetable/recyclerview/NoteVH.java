package in.vilik.tamkapp.timetable.recyclerview;

import android.view.View;
import android.widget.TextView;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.TimetableElement;
import in.vilik.tamkapp.timetable.notes.Note;
import in.vilik.tamkapp.timetable.notes.NoteStorage;
import in.vilik.tamkapp.utils.DataLoader;

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

        TextView title = (TextView) itemView.findViewById(R.id.deadline_title);

        title.setText(note.getNoteType().name() + ": " + note.getName());

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                NoteStorage.removeNote(itemView.getContext(), note);

                if (note.getTimetable() != null) {
                    note.getTimetable().loadData(DataLoader.LoadingStrategy.CACHE_FIRST);
                }

                return true;
            }
        });
    }
}
