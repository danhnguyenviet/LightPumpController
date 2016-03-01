package com.danh.iot;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Danh on 2/20/2016.
 */
public class TemperatureActivity extends AppCompatActivity {

    private TextView tvLocation;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvTemperature;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        tvLocation      = (TextView) findViewById(R.id.textView2);
        tvDate          = (TextView) findViewById(R.id.textView7);
        tvTime          = (TextView) findViewById(R.id.textView9);
        tvTemperature   = (TextView) findViewById(R.id.textView111);

        this.loadTemperatureData();
    }

    /**
     * Load moisture data
     */
    private void loadTemperatureData() {

        if (checkInternetConenction()) {

            if (!MainActivity.temperatureInfo.equals(null)) {
                String[] result = MainActivity.temperatureInfo.split(IotConstant.BR_STRING);

                if (result.length > 1) {

                    tvLocation.setText("Location: " + result[0]); // location
                    tvDate.setText(result[1]); // date
                    tvTime.setText(result[2]); // time
                    tvTemperature.setText(result[3] + "\u00b0" + "C"); // temperature

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
