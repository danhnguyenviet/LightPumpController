package com.danh.iot;

import android.app.DialogFragment;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.danh.iot.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Danh on 3/14/2016.
 */
public class TabhostSystemActivity extends AppCompatActivity {
    public static Integer numOfTextClock = 0; // Started TextClock

    Switch swLight;
    Switch swPump;
    Button btnStartedTime;
    Button btnStoppedTime;
    Button btnScheduledApply;
    Button btnMaxValueApply;
    TextView tvStartedTime;
    TextView tvStoppedTime;
    EditText etMaxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost_system);

        loadControlls();
        loadTabs();

        swLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (checkInternetConenction()) {
                        sendGETRequest(IotConstant.TURN_ON_LIGHT_URL);
                    }
                } else {
                    if (checkInternetConenction()) {
                        sendGETRequest(IotConstant.TURN_OFF_LIGHT_URL);
                    }
                }
            }
        });

        swPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (checkInternetConenction()) {
                        sendGETRequest(IotConstant.TURN_ON_PUMP_URL);
                    }
                } else {
                    if (checkInternetConenction()) {
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
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        });

        btnScheduledApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tvStartedTime.getText().toString().indexOf("AM") != -1) ||
                        (tvStoppedTime.getText().toString().indexOf("PM")) != -1) {
                    String baseStartedTime = tvStartedTime.getText().toString();
                    String tempStartedTime[] = baseStartedTime.split(" ");
                    String startedTime = tempStartedTime[0] + ":" + tempStartedTime[1];

                    String baseStoppedTime = tvStoppedTime.getText().toString();
                    String tempStoppedTime[] = baseStoppedTime.split(" ");
                    String stoppedTime = tempStoppedTime[0] + ":" + tempStoppedTime[1];

                    IotConstant.STARTED_TIME = startedTime;
                    IotConstant.STOPPED_TIME = stoppedTime;

                    IotConstant.refresh();
                    sendGETRequest(IotConstant.SCHEDULE_PUMP);
                    Toast.makeText(getApplicationContext(), "Apply success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "You must set started and stopped time", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Load controls when context is setted
     */
    public void loadControlls() {
        swLight = (Switch) findViewById(R.id.switch1);
        swPump = (Switch) findViewById(R.id.swPump);
        btnStartedTime = (Button) findViewById(R.id.btnStartedTime);
        btnStoppedTime = (Button) findViewById(R.id.btnStoppedTime);
        btnScheduledApply = (Button) findViewById(R.id.btnSheduledApply);
        btnMaxValueApply = (Button) findViewById(R.id.btnMaxValueApply);
        tvStartedTime = (TextView) findViewById(R.id.tvStartedTime);
        tvStoppedTime = (TextView) findViewById(R.id.tvStoppedTime);
        etMaxValue = (EditText) findViewById(R.id.etMaxValue);
    }

    /**
     * Load tabs in tabhost
     */
    public void loadTabs()
    {
        //Lấy Tabhost id ra trước (cái này của built - in android
        final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
        //gọi lệnh setup
        tab.setup();
        TabHost.TabSpec spec;
        //Tạo tab1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Light");
        tab.addTab(spec);
        //Tạo tab2
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Pump");
        tab.addTab(spec);
        //Thiết lập tab mặc định được chọn ban đầu là tab 0
        tab.setCurrentTab(0);
        //Ở đây Tôi để sự kiện này để các bạn tùy xử lý
        //Ví dụ tab1 chưa nhập thông tin xong mà lại qua tab 2 thì báo...
//        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            public void onTabChanged(String arg0) {
//                String s="Tab tag ="+arg0 +"; index ="+
//                        tab.getCurrentTab();
//                Toast.makeText(getApplicationContext(),
//                        s, Toast.LENGTH_SHORT).show();}
//        });
    }

    /**
     * Check the Internet connection. Return true if phone has connection.
     * Retrun false if phone hasn't connection
     * @return
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

    /**
     * Open a Http connection
     * @param urlStr url string is used to open a Http connection
     * @return
     */
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

    /**
     * Send a GET request to server
     * @param urlStr url string is used to make GET request
     */
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
