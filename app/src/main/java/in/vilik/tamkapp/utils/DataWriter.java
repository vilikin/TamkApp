package in.vilik.tamkapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import in.vilik.tamkapp.Debug;

/**
 * Created by vili on 22/04/2017.
 */

public class DataWriter {
    public static void write(Context context, String filename, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context
                    .openFileOutput(filename,
                            Context.MODE_PRIVATE));

            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }

        catch (IOException e) {
            Debug.log("writeToFile()", "File write failed: " + e.toString());
        }
    }

    public static String read(Context context, String filename) {
        String content = null;

        try {
            InputStream inputStream = context
                    .openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                content = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Debug.log("readFromFile()", "File not found: " + e.toString());
        } catch (IOException e) {
            Debug.log("readFromFile()", "Can not read file: " + e.toString());
        }

        return content;
    }
}
