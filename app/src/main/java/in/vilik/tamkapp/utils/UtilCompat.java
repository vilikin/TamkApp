package in.vilik.tamkapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Provides utilities that handle backwards compatibility issues with some Android methods.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class UtilCompat {

    /**
     * Gets drawable from resources.
     *
     * @param context Context
     * @param id      Id of the drawable
     * @return        Drawable object
     */
    public static  Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id, null);
        }
    }
}
