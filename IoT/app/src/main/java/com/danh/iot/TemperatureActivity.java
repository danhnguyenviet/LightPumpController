package com.danh.iot;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.danh.iot.com.danh.iot.thread.ThreadSetting;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Danh on 2/20/2016.
 */
public class TemperatureActivity extends AppCompatActivity {

    private TextView tvLocation;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        tvLocation      = (TextView) findViewById(R.id.tvPumpSystem);
        tvDate          = (TextView) findViewById(R.id.textView7);
        tvTime          = (TextView) findViewById(R.id.textView9);
        tvTemperature   = (TextView) findViewById(R.id.textView111);

        this.readTemperatureFromServer();
    }

    public void readTemperatureFromServer(){
        ThreadSetting threadSetting = new ThreadSetting("GET_TEMPERATURE_INFO", "10.0.3.15", handler);
        threadSetting.start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.getData().getString("flagSetting").toString() == "GET_TEMPERATURE_INFO"){

                if(msg.getData().getString("data").equals(null)){ //Data not exist on Server. Get Default Data
                    Toast.makeText(TemperatureActivity.this, "Can't find data",Toast.LENGTH_SHORT).show();

                }else{ //Data exist on Server
                    Toast.makeText(TemperatureActivity.this, "Loading...",Toast.LENGTH_SHORT).show();
                    getTemperatureInfo(msg.getData().getString("data").toString());
                }
            }

        }
    };

    /**
     * Load moisture data
     */
    private void loadTemperatureData() {

        if (checkInternetConenction()) {
            if (!MainActivity.temperatureInfo.equals(null)) {
//            if (!temperatureInfo.equals(null)) {
                String[] result = MainActivity.temperatureInfo.split(IotConstant.BR_STRING);
//                String[] result = temperatureInfo.split(IotConstant.BR_STRING);

                if (result.length > 1) {

                    tvLocation.setText("Location: " + result[0]); // location
                    tvDate.setText(result[1]); // date
                    tvTime.setText(result[2]); // time
                    tvTemperature.setText(result[3] + "\u00b0" + "C"); // temperature

                }

            }

        }

    }

    private void getTemperatureInfo(String msg) {
        if (!msg.equals(null)) {
            String[] result = msg.split(IotConstant.BR_STRING);

            if (result.length > 1) {

                tvLocation.setText("Location: " + result[0]); // location
                tvDate.setText(result[1]); // date
                tvTime.setText(result[2]); // time
                tvTemperature.setText(result[3] + "\u00b0" + "C"); // temperature

            }
        }
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
