package in.vilik.tamkapp.bottomsheet;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by vili on 23/04/2017.
 */

public class Option {
    private String name;
    private Drawable icon;
    private View.OnClickListener listener;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
