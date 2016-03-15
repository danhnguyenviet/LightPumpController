package com.danh.iot;

import android.app.DialogFragment;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.Toast;

/**
 * Created by Danh on 2/20/2016.
 */
public class SystemActivity extends AppCompatActivity {

    public static Integer numOfTextClock = 0; // Started TextClock

    private Switch sLightAction;
    private Switch sPumpAction;
    private EditText etMaxValue;
    private Button btnStartedTime;
    private TextClock tcStartedTime;
    private Button btnStoppedTime;
    private TextClock tcStoppedTime;
    private Button btnTimeApply;
    private Button btnMaxValueApply;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        getWindow().getDecorView().clearFocus();

        etMaxValue = (EditText) findViewById(R.id.etMaxValue);
        sLightAction = (Switch) findViewById(R.id.switch1);
        sPumpAction = (Switch) findViewById(R.id.swPump);
        btnStartedTime = (Button) findViewById(R.id.btnStartedTime);
        tcStartedTime = (TextClock) findViewById(R.id.tcStartedTime);
        btnStoppedTime = (Button) findViewById(R.id.btnStoppedTime);
        tcStoppedTime = (TextClock) findViewById(R.id.tcStoppedTime);;
        btnTimeApply = (Button) findViewById(R.id.btnSheduledApply);
        btnMaxValueApply = (Button) findViewById(R.id.button4);

        sLightAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (checkInternetConenction()) {

                        BackgroundWorker.url = IotConstant.TURN_ON_LIGHT_URL;
                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                        backgroundWorker.execute("", "", "");

                    }
                } else {
                    if (checkInternetConenction()) {

                        BackgroundWorker.url = IotConstant.TURN_OFF_LIGHT_URL;
                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                        backgroundWorker.execute("", "", "");

                    }
                }
            }
        });

        sPumpAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (checkInternetConenction()) {

                        BackgroundWorker.url = IotConstant.TURN_ON_PUMP_URL;
                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                        backgroundWorker.execute("", "", "");

                    }
                } else {
                    if (checkInternetConenction()) {

                        BackgroundWorker.url = IotConstant.TURN_OFF_PUMP_URL;
                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                        backgroundWorker.execute("", "", "");

                    }
                }
            }
        });

        btnStartedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfTextClock = 0;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        });

        btnStoppedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfTextClock = 1;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });

        btnTimeApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BackgroundWorker.url = IotConstant.SCHEDULE_PUMP;
                String startedTime = tcStartedTime.getText().toString();
                String stoppedTime = tcStoppedTime.getText().toString();

                BackgroundWorker backgroudBackgroundWorker = new BackgroundWorker(getApplicationContext());
                backgroudBackgroundWorker.execute("", startedTime, stoppedTime);

            }
        });

    }

    public void turnOnOffPump() {

        sPumpAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (checkInternetConenction()) {

                        BackgroundWorker.url = IotConstant.TURN_ON_PUMP_URL;
                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                        backgroundWorker.execute("", "", "");

                    } else {

                        BackgroundWorker.url = IotConstant.TURN_OFF_PUMP_URL;
                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                        backgroundWorker.execute("", "", "");

                    }
                }
            }
        });

    }

    /**
     * Check internet connection
     * @return true if internet was connected, false if internet was not connected
     */
    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

}
