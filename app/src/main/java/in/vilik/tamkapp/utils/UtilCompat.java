package in.vilik.tamkapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by vili on 23/04/2017.
 */

public class UtilCompat {
    public static  Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id, null);
        }
    }
}
