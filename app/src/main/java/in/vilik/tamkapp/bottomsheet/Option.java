package in.vilik.tamkapp.bottomsheet;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Implements a single option on a bottom sheet.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0504
 * @since 1.7
 */
public class Option {

    /**
     * Name of the option.
     */
    private String name;

    /**
     * Icon of the option.
     */
    private Drawable icon;

    /**
     * OnClickListener of the option.
     */
    private View.OnClickListener listener;

    /**
     * Gets name of the option.
     *
     * @return Name of the option
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for the option.
     *
     * @param name Name for the option
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets icon of the option.
     *
     * @return Icon of the option
     */
    public Drawable getIcon() {
        return icon;
    }

    /**
     * Sets icon for the option.
     *
     * @param icon Icon for the option
     */
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    /**
     * Gets OnClickListener of the option.
     *
     * @return OnClickListener of the option
     */
    public View.OnClickListener getListener() {
        return listener;
    }

    /**
     * Sets OnClickListener for the option.
     *
     * @param listener OnClickListener for the option
     */
    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
