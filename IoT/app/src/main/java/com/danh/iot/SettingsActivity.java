package com.danh.iot;

import com.danh.iot.com.danh.iot.thread.ThreadSetting;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.danh.iot.com.danh.iot.adapter.AdapterSetting;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danh on 2/29/2016.
 */
public class SettingsActivity extends AppCompatActivity {

    private EditText ipAddress;
    private EditText port;
    private Button btnCancel;

    private ListView lvListFP;
    private ArrayList<String> arrFP;
    private AdapterSetting adapterSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ipAddress = (EditText) findViewById(R.id.ed_setting_ip);
        port = (EditText) findViewById(R.id.ed_setting_port);

        lvListFP = (ListView)findViewById(R.id.lv_setting_list_fp);

//        readSetting();
        //Read Data Setting from Server
        readSettingFromServer();
    }

    //Read Data Setting from Server
    public void readSettingFromServer(){
        ThreadSetting threadSetting = new ThreadSetting("read",getIpOfDevice(),handler);
        threadSetting.start();
    }

    //Save Data Setting to Server
    public void sendSettingToServer(View view){
        String sendIp = ipAddress.getText().toString();
        String sendPort = port.getText().toString();

        //Create String FP and send to Server.( FP = "1,2,3,4,5...")
        String listFP = "";
        for (int i = 0; i<arrFP.size(); i++){
            if(i == (arrFP.size() - 1)){
                listFP += arrFP.get(i).toString();
                break;
            }
            listFP = listFP +  arrFP.get(i).toString() + ",";

        }

        ThreadSetting threadSetting = new ThreadSetting("save",sendIp,sendPort,listFP,handler);
        threadSetting.start();
    }

    //Get ip of Device
    public String getIpOfDevice(){
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

    //Create Handler for thread and update for Screen Setting
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if(msg.getData().getString("flagSetting").toString() == "read"){
                    Toast.makeText(SettingsActivity.this,msg.getData().getString("data"),Toast.LENGTH_SHORT).show();
                    if(msg.getData().getString("data") == "null"){ //Data not exist on Server. Get Default Data
                        Toast.makeText(SettingsActivity.this,"Khong co du lieu",Toast.LENGTH_SHORT).show();
                        createDefaultData();
                    }else{ //Data exist on Server
                        readResultJson(msg.getData().getString("data").toString());
                    }
                }
                if(msg.getData().getString("flagSetting").toString() == "save"){
//                    Toast.makeText(SettingsActivity.this,msg.getData().getString("data").toString(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(SettingsActivity.this,"Saved success...",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    //Read result (Json)
    public void readResultJson(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);

        String resultIP = jsonObject.getString("ip");
        String resultPORT = jsonObject.getString("port");
        String fp = jsonObject.getString("fp");

        String[] listFP = fp.split(",");

        //Set resource for ArrayAdapter
        arrFP = new ArrayList<>();
        for (int i = 0 ; i < listFP.length; i++){
            arrFP.add(listFP[i]);
        }

        //Create adapter setting
        adapterSetting = new AdapterSetting(SettingsActivity.this, R.layout.item_list_setting, arrFP);
        lvListFP.setAdapter(adapterSetting);

        //Set ip and port
        ipAddress.setText(resultIP);
        port.setText(resultPORT);
    }

    //Create Default Data if Data not exist on Server
    public void createDefaultData(){
        arrFP = new ArrayList<>();
        arrFP.add(IotConstant.FP1);
        arrFP.add(IotConstant.FP2);
        arrFP.add(IotConstant.FP3);
        arrFP.add(IotConstant.FP4);
        arrFP.add(IotConstant.FP5);
        arrFP.add(IotConstant.FP6);
        arrFP.add(IotConstant.FP7);
        arrFP.add(IotConstant.FP8);
        arrFP.add(IotConstant.FP9);
        arrFP.add(IotConstant.FP10);

        adapterSetting = new AdapterSetting(SettingsActivity.this, R.layout.item_list_setting, arrFP);
        lvListFP.setAdapter(adapterSetting);

        ipAddress.setText(getIpOfDevice());
        port.setText(IotConstant.PORT);
    }


    //Bo ham na'y
    public void readSetting(){
        BufferedReader bufferedReader = null;

        try {

            File file = getFileStreamPath(IotConstant.SETTINGS_FILE_NAME);
            if(file.exists()) {
                FileInputStream fileInputStream = openFileInput(IotConstant.SETTINGS_FILE_NAME);

                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String line = "";
                String longStr = "";

                while ((line = bufferedReader.readLine()) != null) {
                    longStr += line;
                    longStr += " ";
                }

                String result[] = longStr.split(" ");

                if (result.length > 1) {
                    arrFP = new ArrayList<>();

                    ipAddress.setText(result[0]);
                    port.setText(result[1]);
                    arrFP.add(result[2]);
                    arrFP.add(result[3]);
                    arrFP.add(result[4]);
                    arrFP.add(result[5]);
                    arrFP.add(result[6]);
                    arrFP.add(result[7]);
                    arrFP.add(result[8]);
                    arrFP.add(result[9]);
                    arrFP.add(result[10]);
                    arrFP.add(result[11]);

                    adapterSetting = new AdapterSetting(SettingsActivity.this, R.layout.item_list_setting, arrFP);
                    lvListFP.setAdapter(adapterSetting);
                }
            } else {

                FileOutputStream outputStream;
                outputStream = openFileOutput(IotConstant.SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
                outputStream.write((IotConstant.IP_ADDRESS + "\r\n" +
                        IotConstant.PORT + "\r\n" +
                        IotConstant.FP1 + "\r\n" +
                        IotConstant.FP2 + "\r\n" +
                        IotConstant.FP3 + "\r\n" +
                        IotConstant.FP4 + "\r\n" +
                        IotConstant.FP5 + "\r\n" +
                        IotConstant.FP6 + "\r\n" +
                        IotConstant.FP7 + "\r\n" +
                        IotConstant.FP8 + "\r\n" +
                        IotConstant.FP9 + "\r\n" +
                        IotConstant.FP10 + "\r\n").getBytes());

                outputStream.close();

                readSetting();

            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                Toast.makeText(getApplicationContext(), "Settings was read", Toast.LENGTH_SHORT).show();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    //Bo ham na'y
    public void saveSetting(View view) throws IOException {

        FileOutputStream outputStream;

        try{

            IotConstant.IP_ADDRESS      = ipAddress.getText().toString();
            IotConstant.PORT            = port.getText().toString();
            IotConstant.FP1             = arrFP.get(0);
            IotConstant.FP2             = arrFP.get(1);
            IotConstant.FP3             = arrFP.get(2);
            IotConstant.FP4             = arrFP.get(3);
            IotConstant.FP5             = arrFP.get(4);
            IotConstant.FP6             = arrFP.get(5);
            IotConstant.FP7             = arrFP.get(6);
            IotConstant.FP8             = arrFP.get(7);
            IotConstant.FP9             = arrFP.get(8);
            IotConstant.FP10            = arrFP.get(9);

            outputStream = openFileOutput(IotConstant.SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write((IotConstant.IP_ADDRESS + "\r\n" +
                IotConstant.PORT + "\r\n" +
                IotConstant.FP1 + "\r\n" +
                IotConstant.FP2 + "\r\n" +
                IotConstant.FP3 + "\r\n" +
                IotConstant.FP4 + "\r\n" +
                IotConstant.FP5 + "\r\n" +
                IotConstant.FP6 + "\r\n" +
                IotConstant.FP7 + "\r\n" +
                IotConstant.FP8 + "\r\n" +
                IotConstant.FP9 + "\r\n" +
                IotConstant.FP10 + "\r\n").getBytes());

            outputStream.close();

            readSetting();

            Toast.makeText(getApplicationContext(), "Settings was saved", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            // Do nothing
        }
    }

    public void cancel(View view) {
        finish();
    }
}
