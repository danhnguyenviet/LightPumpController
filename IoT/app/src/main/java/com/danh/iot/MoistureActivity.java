package com.danh.iot;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Danh on 2/20/2016.
 */
public class MoistureActivity extends AppCompatActivity {

    private TextView tvLocation;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvSoilMoisture;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moisture);

        tvLocation      = (TextView) findViewById(R.id.textView12);
        tvDate          = (TextView) findViewById(R.id.textView7);
        tvTime          = (TextView) findViewById(R.id.textView9);
        tvSoilMoisture  = (TextView) findViewById(R.id.textView11);

        this.loadMoistureData();
    }

    /**
     * Load moisture data
     */
    private void loadMoistureData() {

        if (checkInternetConenction()) {

            if (!MainActivity.moistureInfo.equals(null)) {
                String[] result = MainActivity.moistureInfo.split(IotConstant.BR_STRING);

                if (result.length > 1) {

                    tvLocation.setText("Location: " + result[0]); // location
                    tvDate.setText(result[1]); // date
                    tvTime.setText(result[2]); // time
                    tvSoilMoisture.setText(result[3] + "%"); // soil moisture

                }

            }

        }

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
