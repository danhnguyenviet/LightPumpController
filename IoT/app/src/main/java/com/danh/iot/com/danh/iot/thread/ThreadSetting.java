package com.danh.iot.com.danh.iot.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.danh.iot.IotConstant;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by NPhat on 3/3/2016.
 * ThreadSetting read data from server and save data to server
 */
public class ThreadSetting extends Thread {

    private URL url;
    private HttpURLConnection httpURLConnection;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;

    private String flagSetting; //String (save) or (read)
    private String ip;
    private String port;
    private String fp;


    private Handler handler;
    private Message message;
    private Bundle bundle;


    public ThreadSetting(String flagSetting){
        this.flagSetting = flagSetting;
    }

    public ThreadSetting(String flagSetting, String ip, Handler handler){
        this.flagSetting = flagSetting;
        this.ip = ip;
        this.handler = handler;
//        this.message = message;
    }

    public ThreadSetting(String flagSetting, String ip, String port, String fp, Handler handler){
        this.flagSetting = flagSetting;
        this.ip = ip;
        this.port = port;
        this.fp = fp;
        this.handler = handler;
    }



    @Override
    public void run() {
        super.run();
        switch (this.flagSetting){
            case "save":
                saveDataToServer();
                break;
            case "read":
                readDataFromServer();
                break;
            case "GET_TEMPERATURE_INFO":
                getTemperatureInfo();
                break;
            case "GET_MOISTURE_INFO":
                getMoistureInfo();
                break;
        }
    }

    public String getTemperatureInfo() {

        try {

            url = new URL(IotConstant.GET_TEMPERATURE_URL);
            httpURLConnection = (HttpURLConnection)url.openConnection();

            httpURLConnection.connect();

            bundle = new Bundle();
            bundle.putString("flagSetting","GET_TEMPERATURE_INFO");
            bundle.putString("data",readResponse());
            message = Message.obtain();
            message.setData(bundle);
            handler.sendMessage(message);

            return readResponse();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getMoistureInfo() {
        try {
            url = new URL(IotConstant.GET_MOISTURE_URL);
            httpURLConnection = (HttpURLConnection)url.openConnection();

            httpURLConnection.connect();

            bundle = new Bundle();
            bundle.putString("flagSetting","GET_MOISTURE_INFO");
            bundle.putString("data",readResponse());
            message = Message.obtain();
            message.setData(bundle);
            handler.sendMessage(message);

            return readResponse();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void readDataFromServer(){
        try {
            //Set parameter sent.
            String parameter = "ip=" + URLEncoder.encode(this.ip.toString(),"UTF-8");

            url = new URL(IotConstant.READ_DATA_SETTING_URL);
            httpURLConnection = (HttpURLConnection)url.openConnection();

            createRequest(parameter);

            httpURLConnection.connect();

            sendRequest(parameter);

            bundle = new Bundle();
            bundle.putString("flagSetting","read");
            bundle.putString("data",readResponse());
            message = Message.obtain();
            message.setData(bundle);
            handler.sendMessage(message);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveDataToServer() {
        String parameter = null;
        try {
            parameter = "ip=" + URLEncoder.encode(this.ip.toString(), "UTF-8") +
                    "&port=" + URLEncoder.encode(this.port.toString(),"UTF-8") +
                    "&fp=" + URLEncoder.encode(this.fp.toString(), "UTF-8");

            url = new URL(IotConstant.SAVE_DATA_SETTING_URL);
            httpURLConnection = (HttpURLConnection)url.openConnection();

            createRequest(parameter);

            httpURLConnection.connect();

            sendRequest(parameter);

            bundle = new Bundle();
            bundle.putString("flagSetting","save");
            bundle.putString("data",readResponse());
            message = Message.obtain();
            message.setData(bundle);
            handler.sendMessage(message);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void createRequest(String parameter) throws ProtocolException {
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(parameter.getBytes().length));
        httpURLConnection.setRequestProperty("Content-Language", "en-US");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoInput(true);
    }

    //Send parameter to Server
    public void sendRequest(String parameter) throws IOException {
        dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.writeBytes(parameter);
        dataOutputStream.flush();
        dataOutputStream.close();
    }

    //Read response from Server
    public String readResponse() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        String line = "";
        StringBuffer stringBuffer = new StringBuffer("");

        while((line = bufferedReader.readLine()) != null){
            stringBuffer.append(line);
        }

        return stringBuffer.toString();
    }



}
