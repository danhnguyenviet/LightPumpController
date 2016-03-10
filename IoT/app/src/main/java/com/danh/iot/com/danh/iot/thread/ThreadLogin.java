package com.danh.iot.com.danh.iot.thread;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.danh.iot.IotConstant;
import com.danh.iot.com.danh.iot.database.AccountIOT;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NPhat on 8/3/2016.
 * Run Thread do login in background
 */
public class ThreadLogin extends Thread {

    private Message message;
    private Bundle bundle;
    private Handler handler;
    private Activity context;



    private String username;
    private String password;

    /**
     * Thread Login
     * Set value for username, password and handler
     * @param username
     * @param password
     * @param handler
     */
    public ThreadLogin(String username, String password, Handler handler, Activity context){
        this.username = username;
        this.password = password;
        this.handler = handler;
        this.context = context;
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

        AccountIOT accountIOT = new AccountIOT(this.username,this.password,this.context);
        int resultLogin = accountIOT.readAccount();
        bundle = new Bundle();

        switch (resultLogin){
            case IotConstant.KEY_LOGIN_OK:
            {
                bundle.putString("status","1");
                bundle.putString("data","Login success...");
                break;
            }
            case IotConstant.KEY_LOGIN_WRONG_USER:
            {
                bundle.putString("status","2");
                bundle.putString("data","Wrong username...");
                break;
            }
            case IotConstant.KEY_LOGIN_WRONG_PASS:
            {
                bundle.putString("status","3");
                bundle.putString("data","Wrong password...");
                break;
            }
            default:
            {
                bundle.putString("status","0");
                bundle.putString("data","Login again...");
                break;
            }
        }

        message = Message.obtain();
        message.setData(bundle);
        handler.sendMessage(message);
    }


}
