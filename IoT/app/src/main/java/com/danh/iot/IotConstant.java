package com.danh.iot;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

/**
 * Created by Danh on 2/22/2016.
 */
public class IotConstant {

    public static String SERVER_IP_ADDRESS   = "192.168.238.239:8080";

    public static String PORT       = "8080";
    public static String FP1        = "GPIO1";
    public static String FP2        = "GPIO2";
    public static String FP3        = "GPIO3";
    public static String FP4        = "GPIO4";
    public static String FP5        = "GPIO5";
    public static String FP6        = "GPIO6";
    public static String FP7        = "GPIO7";
    public static String FP8        = "GPIO8";
    public static String FP9        = "GPIO9";
    public static String FP10       = "GPIO10";

    public static String STARTED_TIME               = "";
    public static String STOPPED_TIME               = "";

    public static String GET_TEMPERATURE_URL        = "http://" + SERVER_IP_ADDRESS + "/iot/api/get_temperature.php";
    public static String GET_MOISTURE_URL           = "http://" + SERVER_IP_ADDRESS + "/iot/api/get_moisture.php";

    public static String TURN_ON_LIGHT_URL          = "http://" + SERVER_IP_ADDRESS + "/iot/api/api.php?GPIO2=ON";
    public static String TURN_OFF_LIGHT_URL         = "http://" + SERVER_IP_ADDRESS + "/iot/api/api.php?GPIO2=OFF";
    public static String TURN_ON_PUMP_URL           = "http://" + SERVER_IP_ADDRESS + "/iot/api/api.php?GPIO1=ON";
    public static String TURN_OFF_PUMP_URL          = "http://" + SERVER_IP_ADDRESS + "/iot/api/api.php?GPIO1=OFF";
    public static String SCHEDULE_PUMP              = "http://" + SERVER_IP_ADDRESS + "/iot/api/api.php?startON=" +
            STARTED_TIME + "&stopOFF=" + STOPPED_TIME;

    public static String BR_STRING = "<br/>";

    public static String SETTINGS_FILE_NAME = "iot_settings.txt";

    /**
     * CUID DATABASE
     * Code on database
     * Temp ip address
     */
    public static String READ_DATA_SETTING_URL  = "http://" + SERVER_IP_ADDRESS + "/readSettingIOT.php";
    public static String SAVE_DATA_SETTING_URL  = "http://" + SERVER_IP_ADDRESS + "/saveSettingIOT.php";
    public static String LOGIN_URL              = "http://" + SERVER_IP_ADDRESS + "/loginIOT.php";


    /**
     * Account database
     */
    public static String FILE_ACCOUNT           = "account.txt";
    public static String USER_ACCOUNT_DEFAULT   = "admin";
    public static String PASS_ACCOUNT_DEFAULT   = "honeynet.vn";

    /**
     * KEY for login
     */
    public static final int KEY_LOGIN_OK            = 1;
    public static final int KEY_LOGIN_WRONG_PASS    = 2;
    public static final int KEY_LOGIN_WRONG_USER    = 3;


    /**
     * Request and result code for Activity
     */
    //Change password activity
    public static final int REQUEST_CODE_ACTIVITY_CHANGE_PASSWORD = 101;
    public static final int RESULT_CODE_ACTIVITY_CHANGE_PASSWORD = 102;

    /**
     * Refresh value in each string
     */
    public static void refreshSchedulePump() {
        SCHEDULE_PUMP = "http://" + SERVER_IP_ADDRESS + "/iot/api/api.php?startON=" +
                STARTED_TIME + "&stopOFF=" + STOPPED_TIME;
    }

    //File Setting
    public static final String FILE_SETTING = "settings.txt";

    //Get ip of Device
    public static String getIpOfDevice(Activity activity){
        WifiManager wm = (WifiManager) activity.getSystemService(activity.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }
}
