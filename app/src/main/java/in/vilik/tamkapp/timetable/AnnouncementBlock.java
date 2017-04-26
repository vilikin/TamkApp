package in.vilik.tamkapp.timetable;

import android.view.View;

/**
 * Implements announcement block component for timetable.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class AnnouncementBlock implements TimetableElement {

    /**
     * Title of the announcement block.
     */
    private String title;

    /**
     * Body of the announcement block.
     */
    private String body;

    /**
     * Listener that is called when the block is clicked.
     */
    private View.OnClickListener listener;

    /**
     * Gets the OnClickListener of the block.
     *
     * @return  OnClickListener of the block
     */
    public View.OnClickListener getListener() {
        return listener;
    }

    /**
     * Sets OnClickListener for the block.
     * @param listener  OnClickListener for this block
     */
    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Gets title of the block.
     *
     * @return Title of the block
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title for the block.
     *
     * @param title Title for the block
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets body of the block.
     *
     * @return  Body of the block
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body for the block.
     *
     * @param body  Body for the block
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets type of the timetable element.
     *
     * This information is used by recycler view of the timetable
     * to choose correct ViewHolder for the item.
     *
     * @return  Type.ANNOUNCEMENT_BLOCK
     */
    @Override
    public Type getType() {
        return Type.ANNOUNCEMENT_BLOCK;
    }
}
