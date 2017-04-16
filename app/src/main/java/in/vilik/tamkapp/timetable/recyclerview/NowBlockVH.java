package in.vilik.tamkapp.timetable.recyclerview;

import android.view.View;
import android.widget.TextView;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.NowBlock;
import in.vilik.tamkapp.timetable.TimetableElement;

/**
 * Created by vili on 16/04/2017.
 */

class NowBlockVH extends TimetableViewHolder {
    public NowBlockVH(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TimetableElement element) {
        NowBlock nowBlock = (NowBlock) element;
        TextView title = (TextView) itemView.findViewById(R.id.nowBlockTitle);
        title.setText("NYT: " + nowBlock.getReservation().getSubject());
    }
}
