package in.vilik.tamkapp.timetable.recyclerview;

import android.view.View;
import android.widget.TextView;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.AnnouncementBlock;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Implements ViewHolder for announcement block.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
class AnnouncementBlockVH extends TimetableViewHolder {

    /**
     * Initializes view holder.
     *
     * @param itemView  View of the ViewHolder
     */
    public AnnouncementBlockVH(View itemView) {
        super(itemView);
    }

    /**
     * Binds announcement block to this view holder.
     *
     * Fills text elements with data from the announcement block object.
     *
     * @param element   Timetable element representing the announcement block item
     */
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
