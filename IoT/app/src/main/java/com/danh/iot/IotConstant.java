package com.danh.iot;

/**
 * Created by Danh on 2/22/2016.
 */
public class IotConstant {

    public static String IP_ADDRESS     = "192.168.55.248";
    public static String PORT           = "80";
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

    public static String GET_TEMPERATURE_URL        = "http://" + IP_ADDRESS + "/IOT_Server/get_temperature.php";
    public static String GET_MOISTURE_URL           = "http://" + IP_ADDRESS + "/IOT_Server/get_moisture.php";
    public static String TURN_ON_LIGHT_URL          = "http://" + IP_ADDRESS + "/IOT_Server/turn_on_light.php";
    public static String TURN_OFF_LIGHT_URL         = "http://" + IP_ADDRESS + "/IOT_Server/turn_off_light.php";
    public static String TURN_ON_PUMP_URL           = "http://" + IP_ADDRESS + "/IOT_Server/turn_on_pump.php";
    public static String TURN_OFF_PUMP_URL          = "http://" + IP_ADDRESS + "/IOT_Server/turn_off_pump.php";
    public static String SCHEDULE_FOR_MOBILE_URL    = "http://" + IP_ADDRESS + "/IOT_Server/schedule_for_mobile.php";

    public static String BR_STRING = "<br/>";

    public static String SETTINGS_FILE_NAME = "iot_settings.txt";

}
