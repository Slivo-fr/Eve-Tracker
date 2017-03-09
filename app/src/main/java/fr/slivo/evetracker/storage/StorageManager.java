package fr.slivo.evetracker.storage;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.slivo.evetracker.entities.EveAPI;

public class StorageManager {

    protected static String notificationFilename = "processedNotifications.txt";
    protected static String apiFilename = "api.txt";

    public static void saveNotificationId(Context context, String notificationId) {

        FileOutputStream outputStream;

        String notificationIdString = String.format(notificationId + "%n");

        try {
            outputStream = context.openFileOutput(notificationFilename, Context.MODE_APPEND);
            outputStream.write(notificationIdString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void storeAPI(Context context, String api) {

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(apiFilename, Context.MODE_PRIVATE);
            outputStream.write(api.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNotificationNew (Context context, String notificationId) {

        StorageManager.checkDataFile(context);
        String storedNotifications = readFileAsString(context, notificationFilename);

        return !storedNotifications.contains(notificationId);
    }

    protected static void checkDataFile(Context context) {

        File file = new File(context.getFilesDir(), notificationFilename);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFileAsString(Context context, String filename) {

        String result = "";
        File file = new File(context.getFilesDir(), filename);

        if ( file.exists() ) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                char current;
                while (fis.available() > 0) {
                    current = (char) fis.read();
                    result = result + String.valueOf(current);
                }

            } catch (Exception e) {
                Log.d("StorageManager", e.toString());
            } finally {
                if (fis != null)
                    try {
                        fis.close();
                    } catch (IOException ignored) {
                    }
            }
        }
        return result;
    }

    public static EveAPI getStoredApi(Context context) {

        String storedApiString = readFileAsString(context, apiFilename);
        EveAPI api = new EveAPI();

        if (!storedApiString.equals("") && storedApiString.contains(":")) {
            String[] storedValues = storedApiString.split(":");
            api.setKeyId(storedValues[0]);
            api.setvCode(storedValues[1]);
        }

        return api;
    }
}
