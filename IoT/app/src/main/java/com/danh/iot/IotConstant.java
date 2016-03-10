package com.danh.iot;

/**
 * Created by Danh on 2/22/2016.
 */
public class IotConstant {

    public static String DATA_IP_ADDRESS     = "mysql.hostinger.vn";
    public static String SERVER_IP_ADDRESS     = "demoiot.esy.es";

    public static String PORT           = "80";
    public static String FP1        = "1";
    public static String FP2        = "2";
    public static String FP3        = "33";
    public static String FP4        = "4";
    public static String FP5        = "5";
    public static String FP6        = "6";
    public static String FP7        = "7";
    public static String FP8        = "8";
    public static String FP9        = "9";
    public static String FP10       = "10";


    public static String GET_TEMPERATURE_URL        = "http://" + SERVER_IP_ADDRESS + "/IOT_Server/get_temperature.php";
    public static String GET_MOISTURE_URL           = "http://" + SERVER_IP_ADDRESS + "/IOT_Server/get_moisture.php";
    public static String TURN_ON_LIGHT_URL          = "http://" + SERVER_IP_ADDRESS + "/IOT_Server/turn_on_light.php";
    public static String TURN_OFF_LIGHT_URL         = "http://" + SERVER_IP_ADDRESS + "/IOT_Server/turn_off_light.php";
    public static String TURN_ON_PUMP_URL           = "http://" + SERVER_IP_ADDRESS + "/IOT_Server/turn_on_pump.php";
    public static String TURN_OFF_PUMP_URL          = "http://" + SERVER_IP_ADDRESS + "/IOT_Server/turn_off_pump.php";
    public static String SCHEDULE_FOR_MOBILE_URL    = "http://" + SERVER_IP_ADDRESS + "/IOT_Server/schedule_for_mobile.php";

    public static String BR_STRING = "<br/>";

    public static String SETTINGS_FILE_NAME = "iot_settings.txt";

    /**
     * CUID DATABASE
     * Code on database
     * Temp ip address
     */
    public static String READ_DATA_SETTING_URL = "http://" + SERVER_IP_ADDRESS + "/readSettingIOT.php";
    public static String SAVE_DATA_SETTING_URL = "http://" + SERVER_IP_ADDRESS + "/saveSettingIOT.php";
    public static String LOGIN_URL = "http://" + SERVER_IP_ADDRESS + "/loginIOT.php";


    /**
     * Account database
     */
    public static String FILE_ACCOUNT = "account.txt";
    public static String USER_ACCOUNT_DEFAULT = "admin";
    public static String PASS_ACCOUNT_DEFAULT = "honeynet.vn";

    /**
     * KEY for login
     */
    public static final int KEY_LOGIN_OK = 1;
    public static final int KEY_LOGIN_WRONG_PASS = 2;
    public static final int KEY_LOGIN_WRONG_USER = 3;


}
