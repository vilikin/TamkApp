package in.vilik.tamkapp.timetable;

import android.view.View;

/**
 * Created by vili on 19/04/2017.
 */

public class AnnouncementBlock implements TimetableElement {
    private String title;
    private String body;
    private View.OnClickListener listener;

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public Type getType() {
        return Type.ANNOUNCEMENT_BLOCK;
    }
}
