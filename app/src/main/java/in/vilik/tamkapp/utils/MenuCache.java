package in.vilik.tamkapp.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;

import in.vilik.tamkapp.menus.MenuType;

/**
 * Created by vili on 13/04/2017.
 */

public class MenuCache {
    public static void writeToFile(Context context, MenuType menuType, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(menuType.name() + ".json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }

        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
