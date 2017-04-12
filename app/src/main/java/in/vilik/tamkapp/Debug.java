package in.vilik.tamkapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by vili on 06/03/2017.
 */

public class Debug {
    private static final boolean SHOW_DEBUG = true;

    public static void log(String method, String msg) {
        if (SHOW_DEBUG) {
            Log.d("TAG", method + ": " + msg);
        }
    }
}
