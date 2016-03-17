package com.danh.iot;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Danh on 3/17/2016.
 */
public class InteractingFile {
    /**
     * Return true if file is exist else return false
     * @param context
     * @param fileName
     * @return
     */
    public static boolean isExist(Context context, String fileName) {
        File file = context.getFileStreamPath(fileName);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get settings information from settings file
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    public static ArrayList<String> getSettingsInfoFromFile(Context context, String fileName) throws IOException {
        FileInputStream fileInputStream = context.openFileInput(fileName);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String line = "";
        ArrayList<String> arrayList = new ArrayList<>();

        while((line = bufferedReader.readLine()) != null){
            arrayList.add(line);
        }

        return arrayList;
    }

    /**
     * Get IP from settings file
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    @Nullable
    public static String getIPFromSettingsFile(Context context, String fileName) throws IOException {
        ArrayList<String> settingsInfo = getSettingsInfoFromFile(context, fileName);
        if(settingsInfo.size() != 0) {
            return settingsInfo.get(0);
        } else {
            return null;
        }
    }

    /**
     * Get Port from settings file
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    @Nullable
    public static String getPortFromSettingsFile(Context context, String fileName) throws IOException {
        ArrayList<String> settingsInfo = getSettingsInfoFromFile(context, fileName);
        if(settingsInfo.size() != 0) {
            return settingsInfo.get(1);
        } else {
            return null;
        }
    }

    /**
     * Get FP list from settings file
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    @Nullable
    public static String[] getFpListFromSettingsFile(Context context, String fileName) throws IOException {
        ArrayList<String> settingsInfo = getSettingsInfoFromFile(context, fileName);
        if(settingsInfo.size() != 0) {
            String[] fpList = settingsInfo.get(2).split(",");
            return fpList;
        } else {
            return null;
        }
    }
}
