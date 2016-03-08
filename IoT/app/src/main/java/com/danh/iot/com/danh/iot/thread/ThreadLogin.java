package com.danh.iot.com.danh.iot.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.danh.iot.IotConstant;
import com.danh.iot.MD5;

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
 * Created by NPhat on 8/3/2016.
 */
public class ThreadLogin extends Thread {

    private URL url;
    private HttpURLConnection httpURLConnection;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;

    private Message message;
    private Bundle bundle;
    private Handler handler;



    private String username;
    private String password;

    /**
     * Thread Login
     * Set value for username, password and handler
     * @param username
     * @param password
     * @param handler
     */
    public ThreadLogin(String username, String password, Handler handler){
        this.username = username;
        this.password = MD5.crypt(password);
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            sendDataLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send username and password to Server and check. if ok is result OK and else.
     * @throws IOException
     */
    public void sendDataLogin() throws IOException {
        String parameter = "username=" + URLEncoder.encode(this.username,"UTF-8")
                + "&password=" + URLEncoder.encode(this.password,"UTF-8");

        url = new URL(IotConstant.LOGIN_URL);
        httpURLConnection = (HttpURLConnection)url.openConnection();

        createRequest(parameter);

        sendRequest(parameter);

        bundle = new Bundle();
        bundle.putString("data",readResponse());
        message = Message.obtain();
        message.setData(bundle);

        handler.sendMessage(message);
    }


    /**
     * Create requests for httpURLConnection. Prepare send Data to Server
     * @param parameter
     * @throws ProtocolException
     */
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
