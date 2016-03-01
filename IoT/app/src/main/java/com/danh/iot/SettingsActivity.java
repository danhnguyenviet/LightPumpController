package com.danh.iot;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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

        readSetting();
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
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }

}
