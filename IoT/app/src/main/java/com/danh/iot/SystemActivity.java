package com.danh.iot;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
    private TextView tvStartedTime;
    private TextView tvStoppedTime;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        getWindow().getDecorView().clearFocus();

        etMaxValue = (EditText) findViewById(R.id.editText2);
        sLightAction = (Switch) findViewById(R.id.switch1);
        sPumpAction = (Switch) findViewById(R.id.switch2);
        btnStartedTime = (Button) findViewById(R.id.button);
        tcStartedTime = (TextClock) findViewById(R.id.textClock);
        btnStoppedTime = (Button) findViewById(R.id.button2);
        tcStoppedTime = (TextClock) findViewById(R.id.textClock2);;
        btnTimeApply = (Button) findViewById(R.id.button3);
        btnMaxValueApply = (Button) findViewById(R.id.button4);
        tvStartedTime = (TextView) findViewById(R.id.tvStartedTime);
        tvStoppedTime = (TextView) findViewById(R.id.tvStoppedTime);

        sLightAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (checkInternetConenction()) {

//                        BackgroundWorker.url = IotConstant.TURN_ON_LIGHT_URL;
//                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
//                        backgroundWorker.execute("", "", "");

                        sendGETRequest(IotConstant.TURN_ON_LIGHT_URL);

                    }
                } else {
                    if (checkInternetConenction()) {

//                        BackgroundWorker.url = IotConstant.TURN_OFF_LIGHT_URL;
//                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
//                        backgroundWorker.execute("", "", "");

                        sendGETRequest(IotConstant.TURN_OFF_LIGHT_URL);

                    }
                }
            }
        });

        sPumpAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (checkInternetConenction()) {

//                        BackgroundWorker.url = IotConstant.TURN_ON_PUMP_URL;
//                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
//                        backgroundWorker.execute("", "", "");

                        sendGETRequest(IotConstant.TURN_ON_PUMP_URL);

                    }
                } else {
                    if (checkInternetConenction()) {

//                        BackgroundWorker.url = IotConstant.TURN_OFF_PUMP_URL;
//                        BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
//                        backgroundWorker.execute("", "", "");

                        sendGETRequest(IotConstant.TURN_OFF_PUMP_URL);

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
                if ((tvStartedTime.getText().toString().indexOf("AM") != -1) ||
                        (tvStoppedTime.getText().toString().indexOf("PM")) != -1) {
//                BackgroundWorker.url = IotConstant.SCHEDULE_FOR_MOBILE_URL;
                    String baseStartedTime = tvStartedTime.getText().toString();
                    String tempStartedTime[] = baseStartedTime.split(" ");
                    String startedTime = tempStartedTime[0] + ":" + tempStartedTime[1];

                    String baseStoppedTime = tvStoppedTime.getText().toString();
                    String tempStoppedTime[] = baseStoppedTime.split(" ");
                    String stoppedTime = tempStoppedTime[0] + ":" + tempStoppedTime[1];

//                BackgroundWorker backgroudBackgroundWorker = new BackgroundWorker(getApplicationContext());
//                backgroudBackgroundWorker.execute("", startedTime, stoppedTime);

                    IotConstant.STARTED_TIME = startedTime;
                    IotConstant.STOPPED_TIME = stoppedTime;

                    IotConstant.refresh();
                    sendGETRequest(IotConstant.SCHEDULE_PUMP);
                    Toast.makeText(SystemActivity.this, "Apply success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SystemActivity.this, "You must set started and stopped time", Toast.LENGTH_SHORT).show();
                }
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

    private InputStream openHttpConnection(String urlStr) {
        InputStream in = null;
        int resCode = -1;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    private void sendGETRequest(String urlStr) {

        final String url = urlStr;

        new Thread() {
            public void run() {
                InputStream in = null;

                Message msg = Message.obtain();
                msg.what = 1;

                try {
                    in = openHttpConnection(url);

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = "", content = "";
                    while((line = bufferedReader.readLine()) != null) {
                        content += line;
                    }

                    if (content == "")
                        System.out.println("Test 1 - null");

                    in.close();
                }

                catch (IOException e1) {
                    e1.printStackTrace();
                }
//                messageHandler.sendMessage(msg);
            }
        }.start();

    }

}