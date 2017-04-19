package in.vilik.tamkapp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.R;

/**
 * Created by vili on 13/04/2017.
 */

public class ErrorToaster {
    static final int TOAST_LENGTH = Toast.LENGTH_LONG;
    public static final int ERROR_FAILED_TO_PARSE = R.string.error_failed_to_parse;
    public static final int ERROR_FAILED_TO_LOAD = R.string.error_failed_to_load;

    public static void show(final Activity activity, int errorTextId) {
        final String errorText = activity.getResources().getString(errorTextId);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, errorText, ErrorToaster.TOAST_LENGTH)
                        .show();
            }
        });

        Debug.log("ERROR", errorText);
    }
}
