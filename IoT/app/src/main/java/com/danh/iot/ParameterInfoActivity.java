package com.danh.iot;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    private CheckBox cbSetHumidityLimitation;
    private TextView tvHighHumidity;
    private TextView tvLowHumidity;
    private EditText etHighHumidity;
    private EditText etLowHumidity;
    private Button btnApplyHumidityLimitation;
    private CheckBox cbSetMoistureLimitation;
    private TextView tvHighMoisture;
    private TextView tvLowMoisture;
    private EditText etHighMoisture;
    private EditText etLowMoisture;
    private Button btnApplyMoistureLimitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_info);

        // Refresh settings information
        if (InteractingFile.isExist(this.getApplicationContext(), IotConstant.FILE_SETTING)) {
            IotConstant.refreshSettingsInfo(this.getApplicationContext(), IotConstant.FILE_SETTING);
        }

        loadTabs();
        loadControlls();
        readTemperatureFromServer();
        readMoistureFromServer();

        cbSetHumidityLimitation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setHumidityLimitationVisualization();
            }
        });

        cbSetMoistureLimitation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMoistureLimitationVisualization();
            }
        });
    }

    /**
     * Set the vizualiztion of humidity limitation
     */
    public void setHumidityLimitationVisualization() {
        if (cbSetHumidityLimitation.isChecked()) {
            tvHighHumidity.setVisibility(View.VISIBLE);
            tvLowHumidity.setVisibility(View.VISIBLE);
            etHighHumidity.setVisibility(View.VISIBLE);
            etLowHumidity.setVisibility(View.VISIBLE);
            btnApplyHumidityLimitation.setVisibility(View.VISIBLE);
        } else {
            tvHighHumidity.setVisibility(View.INVISIBLE);
            tvLowHumidity.setVisibility(View.INVISIBLE);
            etHighHumidity.setVisibility(View.INVISIBLE);
            etLowHumidity.setVisibility(View.INVISIBLE);
            btnApplyHumidityLimitation.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Set the vizualiztion of moisture limitation
     */
    public void setMoistureLimitationVisualization() {
        if (cbSetMoistureLimitation.isChecked()) {
            tvHighMoisture.setVisibility(View.VISIBLE);
            tvLowMoisture.setVisibility(View.VISIBLE);
            etHighMoisture.setVisibility(View.VISIBLE);
            etLowMoisture.setVisibility(View.VISIBLE);
            btnApplyMoistureLimitation.setVisibility(View.VISIBLE);
        } else {
            tvHighMoisture.setVisibility(View.INVISIBLE);
            tvLowMoisture.setVisibility(View.INVISIBLE);
            etHighMoisture.setVisibility(View.INVISIBLE);
            etLowMoisture.setVisibility(View.INVISIBLE);
            btnApplyMoistureLimitation.setVisibility(View.INVISIBLE);
        }
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

        cbSetHumidityLimitation     = (CheckBox) findViewById(R.id.cbSetHumidityLimitation);
        tvHighHumidity              = (TextView) findViewById(R.id.tvHighHumidity);
        tvLowHumidity                = (TextView) findViewById(R.id.tvLowHumidity);
        etHighHumidity              = (EditText) findViewById(R.id.etHighHumidity);
        etLowHumidity               = (EditText) findViewById(R.id.etLowHumidity);
        btnApplyHumidityLimitation  = (Button) findViewById(R.id.btnApplyHumidityLimitation);

        cbSetMoistureLimitation     = (CheckBox) findViewById(R.id.cbSetMoistureLimitation);
        tvHighMoisture              = (TextView) findViewById(R.id.tvHighMoisture);
        tvLowMoisture                = (TextView) findViewById(R.id.tvLowMoisture);
        etHighMoisture              = (EditText) findViewById(R.id.etHighMoisture);
        etLowMoisture               = (EditText) findViewById(R.id.etLowMoisture);
        btnApplyMoistureLimitation  = (Button) findViewById(R.id.btnApplyMoistureLimitation);
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
