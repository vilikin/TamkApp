package in.vilik.tamkapp;

import android.util.Log;

/**
 * Implements debugging utility to toggle debugging on/off from one place if necessary.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0425
 * @since 1.7
 */
public class Debug {
    /**
     * Indicates if debug messages should be logged or not.
     */
    private static final boolean SHOW_DEBUG = true;

    /**
     * Logs a single message to the console.
     *
     * @param method    Method that logged this message
     * @param msg       Message to be logged
     */
    public static void log(String method, String msg) {
        if (SHOW_DEBUG) {
            Log.d("TAG", method + ": " + msg);
        }
    }
}
