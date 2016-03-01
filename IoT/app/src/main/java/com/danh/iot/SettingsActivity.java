package com.danh.iot;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Danh on 2/29/2016.
 */
public class SettingsActivity extends AppCompatActivity {

    private EditText ipAddress;
    private EditText port;
    private EditText fp1;
    private EditText fp2;
    private EditText fp3;
    private EditText fp4;
    private EditText fp5;
    private EditText fp6;
    private EditText fp7;
    private EditText fp8;
    private EditText fp9;
    private EditText fp10;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ipAddress = (EditText) findViewById(R.id.editText);
        port = (EditText) findViewById(R.id.editText13);

        fp1 = (EditText) findViewById(R.id.editText3);
        fp2 = (EditText) findViewById(R.id.editText4);
        fp3 = (EditText) findViewById(R.id.editText5);
        fp4 = (EditText) findViewById(R.id.editText6);
        fp5 = (EditText) findViewById(R.id.editText7);
        fp6 = (EditText) findViewById(R.id.editText8);
        fp7 = (EditText) findViewById(R.id.editText9);
        fp8 = (EditText) findViewById(R.id.editText10);
        fp9 = (EditText) findViewById(R.id.editText11);
        fp10 = (EditText) findViewById(R.id.editText12);
        btnSave = (Button) findViewById(R.id.button7);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                try {
                    saveSettingsToServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        readSetting();
    }

    public void readSetting(){
        BufferedReader bufferedReader = null;

        try {

            File file = getFileStreamPath(IotConstant.SETTINGS_FILE_NAME);
            if(file.exists()) {
                FileInputStream fileInputStream = openFileInput(IotConstant.SETTINGS_FILE_NAME);

                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String line = "";
                String longStr = "";

                while ((line = bufferedReader.readLine()) != null){
                    longStr += line;
                    longStr += " ";
                }

                String result[] = longStr.split(" ");

                if (result.length > 1) {
                    ipAddress.setText(result[0]);
                    port.setText(result[1]);
                    fp1.setText(result[2]);
                    fp2.setText(result[3]);
                    fp3.setText(result[4]);
                    fp4.setText(result[5]);
                    fp5.setText(result[6]);
                    fp6.setText(result[7]);
                    fp7.setText(result[8]);
                    fp8.setText(result[9]);
                    fp9.setText(result[10]);
                    fp10.setText(result[11]);
                }

                bufferedReader.close();
                Toast.makeText(getApplicationContext(), "Settings was read", Toast.LENGTH_SHORT).show();

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

        }

    }

    public void saveSetting(View view) throws IOException {

        FileOutputStream outputStream;

        try{

            IotConstant.IP_ADDRESS      = ipAddress.getText().toString();
            IotConstant.PORT            = port.getText().toString();
            IotConstant.FP1             = fp1.getText().toString();
            IotConstant.FP2             = fp2.getText().toString();
            IotConstant.FP3             = fp3.getText().toString();
            IotConstant.FP4             = fp4.getText().toString();
            IotConstant.FP5             = fp5.getText().toString();
            IotConstant.FP6             = fp6.getText().toString();
            IotConstant.FP7             = fp7.getText().toString();
            IotConstant.FP8             = fp8.getText().toString();
            IotConstant.FP9             = fp9.getText().toString();
            IotConstant.FP10            = fp10.getText().toString();

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

    private void saveSettingsToServer() throws IOException {
        String strIp = ipAddress.getText().toString();
        String strPort = port.getText().toString();
        String strFp = fp1.getText().toString() +
                fp2.getText().toString() + fp3.getText().toString() + fp4.getText().toString() +
                fp5.getText().toString() + fp6.getText().toString() + fp7.getText().toString() +
                fp8.getText().toString() + fp9.getText().toString() + fp10.getText().toString();

        try {
            saveToServer(strIp, strPort, strFp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToServer (String strIp, String strPort, String strFp) throws IOException {

        String url = IotConstant.SAVE_SETTINGS_URL;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        String urlParameters = "ip=" + IotConstant.IP_ADDRESS + "&port=" + IotConstant.PORT +
                "&fp=" + IotConstant.FP1 + "," + IotConstant.FP2 + "," + IotConstant.FP3 + "," +
                IotConstant.FP4 + "," + IotConstant.FP5 + "," + IotConstant.FP6 + "," +
                IotConstant.FP7 + "," + IotConstant.FP8 + "," + IotConstant.FP9 + "," +
                IotConstant.FP10;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

}
