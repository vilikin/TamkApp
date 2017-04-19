package in.vilik.tamkapp.timetable.recyclerview;

/**
 * Created by vili on 19/04/2017.
 */

import android.view.View;
import android.widget.TextView;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.AnnouncementBlock;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Created by vili on 16/04/2017.
 */

class AnnouncementBlockVH extends TimetableViewHolder {
    public AnnouncementBlockVH(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TimetableElement element) {
        AnnouncementBlock announcementBlock = (AnnouncementBlock) element;

        TextView title = (TextView) itemView.findViewById(R.id.announcementBlockTitle);
        TextView body = (TextView) itemView.findViewById(R.id.announcementBlockBody);

        title.setText(announcementBlock.getTitle());
        body.setText(announcementBlock.getBody());

        if (announcementBlock.getListener() != null) {
            itemView.setOnClickListener(announcementBlock.getListener());
        }
    }
}
