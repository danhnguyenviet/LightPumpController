package com.danh.iot.com.danh.iot.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.danh.iot.IotConstant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by NPhat on 14/3/2016.
 */
public class ThreadSettingsFile extends Thread {

    private Activity context;
    private Handler handler;

    private String flagSetting; //read or save
    private String ip;
    private String port;
    private String[] fp;

    public ThreadSettingsFile(String ip, String port, String[] fp, Activity context, Handler handler, String flagSetting){
        this.ip = ip;
        this.port = port;
        this.fp = fp;

        this.context = context;
        this.handler = handler;
        this.flagSetting = flagSetting;
    }

    public ThreadSettingsFile(Activity context, Handler handler, String flagSetting){
        this.context = context;
        this.handler = handler;
        this.flagSetting = flagSetting;
    }

    @Override
    public void run() {
        super.run();
        switch (flagSetting){
            case "read":
            {
                try {
                    readSettingsFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "save":
            {
                try {
                    saveSettingsFile(this.ip,this.port,this.fp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }

    /**
     * Read values setting from file
     * @throws IOException
     */
    public void readSettingsFile() throws IOException {
        File file = this.context.getFileStreamPath(IotConstant.FILE_SETTING);
        if(file.exists()){ //Read file if it exist
            FileInputStream fileInputStream = this.context.openFileInput(file.getName());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = "";
            ArrayList<String> arrayList = new ArrayList<>();

            while((line = bufferedReader.readLine()) != null){
                arrayList.add(line);
            }

            if(arrayList.size() != 0){
                String ip = arrayList.get(0);
                String port = arrayList.get(1);
                String[] fp = arrayList.get(2).split(",");

                Bundle bundle = new Bundle();
                bundle.putString("flagSetting","readFile");
                bundle.putString("ip",ip);
                bundle.putString("port",port);
                bundle.putStringArray("fp", fp);
                bundle.putBoolean("null", false);

                Message message = Message.obtain();
                message.setData(bundle);

                handler.sendMessage(message);

            }else{//File exist but null data
                Bundle bundle = new Bundle();
                bundle.putString("flagSetting","readFile");
                bundle.putBoolean("null",true);

                Message message = Message.obtain();
                message.setData(bundle);

                handler.sendMessage(message);
            }
        }else { //No file
            //Save data default
//            saveSettingsFile(IotConstant.getIpOfDevice(this.context),IotConstant.PORT,IotConstant.ARRAY_STRING_DEFAULT);
//            readSettingsFile();
            Bundle bundle = new Bundle();
            bundle.putString("flagSetting","readFile");
            bundle.putBoolean("null",true);

            Message message = Message.obtain();
            message.setData(bundle);

            handler.sendMessage(message);

        }
    }

    /**
     * Save values setting to file
     * @param ip
     * @param port
     * @param fp
     * @throws IOException
     */
    public void saveSettingsFile(String ip, String port, String[] fp) throws IOException {
        FileOutputStream fileOutputStream = this.context.openFileOutput(IotConstant.FILE_SETTING, this.context.MODE_PRIVATE);

        String strFP = "";

        for (int i = 0; i< fp.length; i++){
            if(i == (fp.length - 1)){
                strFP += fp[i];
            }
            strFP = strFP + fp[i] + ",";
        }

        String saveFP = ip + "\r\n" +
                port + "\r\n" +
                strFP;

        fileOutputStream.write(saveFP.getBytes());

        fileOutputStream.close();

        Bundle bundle = new Bundle();
        bundle.putString("flagSetting", "saveFile");
        Message message = Message.obtain();
        message.setData(bundle);

        handler.sendMessage(message);
    }


}
