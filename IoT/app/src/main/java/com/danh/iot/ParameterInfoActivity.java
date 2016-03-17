package com.danh.iot;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.danh.iot.com.danh.iot.thread.ThreadSetting;

public class ParameterInfoActivity extends Activity {
    private TextView tvLocation_0;
    private TextView tvDate_0;
    private TextView tvTime_0;
    private TextView tvTemperature;
    private TextView tvLocation_1;
    private TextView tvDate_1;
    private TextView tvTime_1;
    private TextView tvSoilMoisture;
    private TextView tvLocation_2;
    private TextView tvDate_2;
    private TextView tvTime_2;
    private TextView tvHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_info);

        loadTabs();
        loadControlls();
        readTemperatureFromServer();
        readMoistureFromServer();
    }

    /**
     * Load controlls to context
     */
    public void loadControlls() {
        tvLocation_0        = (TextView) findViewById(R.id.tvLocationTemperature);
        tvDate_0            = (TextView) findViewById(R.id.tvDateTemperature);
        tvTime_0            = (TextView) findViewById(R.id.tvTimeTemperature);
        tvTemperature       = (TextView) findViewById(R.id.tvTemperatureValue);

        tvLocation_1        = (TextView) findViewById(R.id.tvLocationMoisture);
        tvDate_1            = (TextView) findViewById(R.id.tvDateMoisture);
        tvTime_1            = (TextView) findViewById(R.id.tvTimeMoisture);
        tvSoilMoisture      = (TextView) findViewById(R.id.tvMoistureValue);

        tvLocation_2      = (TextView) findViewById(R.id.tvLocationHumidity);
        tvDate_2          = (TextView) findViewById(R.id.tvDateHumidity);
        tvTime_2          = (TextView) findViewById(R.id.tvTimeHumidity);
        tvHumidity        = (TextView) findViewById(R.id.tvHumidityValue);
    }

    /**
     * Load tabs in tabhost
     */
    public void loadTabs()
    {
        final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
        tab.setup();
        TabHost.TabSpec spec;

        // Create tab1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Temperature");
        tab.addTab(spec);
        // Create tab2
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Moisture");
        tab.addTab(spec);
        // Create tab3
        spec = tab.newTabSpec("t3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Humidity");
        tab.addTab(spec);
        // Default tab is tab 0
        tab.setCurrentTab(0);
    }

    public void readTemperatureFromServer(){
        ThreadSetting threadSetting = new ThreadSetting("GET_TEMPERATURE_INFO", "10.0.3.15", handler_0);
        threadSetting.start();
    }

    private Handler handler_0 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.getData().getString("flagSetting").toString() == "GET_TEMPERATURE_INFO"){

                if(msg.getData().getString("data").equals(null)){ //Data not exist on Server. Get Default Data
                    Toast.makeText(getApplicationContext(), "Can't find data",Toast.LENGTH_SHORT).show();

                }else{ //Data exist on Server
                    Toast.makeText(getApplicationContext(), "Loading...",Toast.LENGTH_SHORT).show();
                    getTemperatureInfo(msg.getData().getString("data").toString());
                }
            }

        }
    };

    private void getTemperatureInfo(String msg) {
        if (!msg.equals(null)) {
            String[] result = msg.split(IotConstant.BR_STRING);

            if (result.length > 1) {

                tvLocation_0.setText("Location: " + result[0]); // location
                tvDate_0.setText(result[1]); // date
                tvTime_0.setText(result[2]); // time
                tvTemperature.setText(result[3] + "\u00b0" + "C"); // temperature

            }
        }
    }

    public void readMoistureFromServer(){
        ThreadSetting threadSetting = new ThreadSetting("GET_MOISTURE_INFO", "10.0.3.15", handler_1);
        threadSetting.start();
    }

    private Handler handler_1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.getData().getString("flagSetting").toString() == "GET_MOISTURE_INFO"){

                if(msg.getData().getString("data").equals(null)){ //Data not exist on Server. Get Default Data
                    Toast.makeText(getApplicationContext(), "Can't find data", Toast.LENGTH_SHORT).show();

                }else{ //Data exist on Server
                    Toast.makeText(getApplicationContext(), "Loading...",Toast.LENGTH_SHORT).show();
                    getMoistureInfo(msg.getData().getString("data").toString());
                }
            }

        }
    };

    private void getMoistureInfo(String msg) {
        if (!msg.equals(null)) {
            String[] result = msg.split(IotConstant.BR_STRING);

            if (result.length > 1) {

                tvLocation_1.setText("Location: " + result[0]); // location
                tvDate_1.setText(result[1]); // date
                tvTime_1.setText(result[2]); // time
                tvSoilMoisture.setText(result[3] + "%"); // soil moisture

            }
        }
    }
}
