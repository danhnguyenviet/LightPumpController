package com.danh.iot;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BackgroundWorker backgroudWorkerForTemperature;
    BackgroundWorker backgroudWorkerForMoisture;
    public static String temperatureInfo = "";
    public static String moistureInfo = "";

    private TextView actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkInternetConenction();
    }

    public void callSystemActivity(View view) {
        Intent intent = new Intent(this, SystemActivity.class);
        startActivity(intent);
    }

    public void callTemperatureActivity(View view) {
        BackgroundWorker.url = IotConstant.GET_TEMPERATURE_URL;
        backgroudWorkerForTemperature = new BackgroundWorker(this);
        backgroudWorkerForTemperature.execute("", "", "");

        Intent intent = new Intent(this, TemperatureActivity.class);
        startActivity(intent);
    }

    public void callMoistureActivity(View view) {
        BackgroundWorker.url = IotConstant.GET_MOISTURE_URL;
        backgroudWorkerForMoisture = new BackgroundWorker(this);
        backgroudWorkerForMoisture.execute("", "", "");

        Intent intent = new Intent(this, MoistureActivity.class);
        startActivity(intent);
    }

    public void callSettingActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void callAboutActivity(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }


    /**
     * Create menu on action bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Do event when select item on Menu Action Bar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_acction_bar_change_password:
            {
                Intent intent = new Intent(MainActivity.this,ChangePasswordActivity.class);
                startActivityForResult(intent,IotConstant.REQUEST_CODE_ACTIVITY_CHANGE_PASSWORD);
                break;
            }
            case R.id.menu_action_bar_guide:
            {
                Intent intent = new Intent(MainActivity.this,GuideActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Get result from another Activity. Base on resultCode, data do anything you want.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Activity Change Password
        if(requestCode == IotConstant.REQUEST_CODE_ACTIVITY_CHANGE_PASSWORD){
            switch (resultCode){
                case IotConstant.RESULT_CODE_ACTIVITY_CHANGE_PASSWORD:
                {
                    boolean resultChanged = data.getBooleanExtra("changed",false);
                    if(resultChanged == true){
                        Toast.makeText(MainActivity.this,"Changed password success...",Toast.LENGTH_SHORT).show();
                    }
                    break;
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