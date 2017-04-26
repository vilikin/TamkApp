package in.vilik.tamkapp.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import in.vilik.tamkapp.Debug;

/**
 * Implements methods to read and write files.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class DataStorage {

    /**
     * Writes data to a file. Creates file if it doesn't exist.
     *
     * @param context   Context
     * @param filename  File to write the data in
     * @param data      Data to write to the file
     */
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

    /**
     * Reads data from a file.
     *
     * @param context   Context
     * @param filename  Filename
     * @return          Content of the file, null if file is not found
     */
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
