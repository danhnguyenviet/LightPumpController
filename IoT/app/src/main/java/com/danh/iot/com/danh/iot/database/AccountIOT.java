package com.danh.iot.com.danh.iot.database;

import android.app.Activity;
import android.util.Log;

import com.danh.iot.IotConstant;
import com.danh.iot.MD5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by NPhat on 9/3/2016.
 * AccountIOT read from file and save info Account to file
 */
public class AccountIOT {
    private String username;
    private String password;
    private Activity context;

    private String currentPassword;
    private String newPassword;

    /**
     * Constructer AccountIOT for Login Activity
     *
     * @param username
     * @param password
     * @param context
     */
    public AccountIOT(String username, String password, Activity context){
        this.username = username;
        this.password = MD5.crypt(password);
        this.context = context;
    }

    /**
     * Constructer for Activity Change password
     * @param context
     */
    public AccountIOT(Activity context){
        this.context = context;
    }

    /**
     * Read info account saved on device
     * @return 1-OK/ 2-Wrongpass/ 3-Wronguser
     * @throws IOException
     */
    public int readAccount() throws IOException {
        File file = context.getFileStreamPath(IotConstant.FILE_ACCOUNT);
        if(file.exists()){
            FileInputStream fileInputStream = this.context.openFileInput(file.getName());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line="";
            ArrayList<String> account = new ArrayList<>();

            while((line = bufferedReader.readLine()) != null){
                account.add(line);
            }

            fileInputStream.close();

            if (this.username.equals(account.get(0))){
                if(this.password.equals(account.get(1))){
                    return IotConstant.KEY_LOGIN_OK; //OK
                }else{
                    return IotConstant.KEY_LOGIN_WRONG_PASS; //Wrong password
                }
            }else{
                return IotConstant.KEY_LOGIN_WRONG_USER; //Wrong username
            }
        }else{
            saveAccount(IotConstant.USER_ACCOUNT_DEFAULT, IotConstant.PASS_ACCOUNT_DEFAULT);
        }
        return 0; //Function fail
    }


    /**
     * Save info account to file in device
     * @param username
     * @param password type not crypt md5
     * @throws IOException
     */
    public boolean saveAccount(String username, String password) throws IOException {

        FileOutputStream fileOutputStream = this.context.openFileOutput(IotConstant.FILE_ACCOUNT, this.context.MODE_PRIVATE);

        String strSave = username + "\r\n" + MD5.crypt(password);

        fileOutputStream.write(strSave.getBytes());

        fileOutputStream.close();

        return true;
    }


    /**
     * Check input current password == password data. If ok is save and else.
     * @param currentPassword
     * @param newPassword
     * @return true if OK else false
     * @throws IOException
     */
    public boolean checkPassword(String currentPassword, String newPassword) throws IOException {
        currentPassword = MD5.crypt(currentPassword);

        File file = context.getFileStreamPath(IotConstant.FILE_ACCOUNT);
        if(file.exists()){
            FileInputStream fileInputStream = this.context.openFileInput(IotConstant.FILE_ACCOUNT);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line="";
            ArrayList<String> arrAccount = new ArrayList<>();

            while((line = bufferedReader.readLine()) != null){
                arrAccount.add(line);
            }

            fileInputStream.close();

            if(arrAccount.get(1).equals(currentPassword)){
                return saveAccount(arrAccount.get(0),newPassword);
            }else{
                return false;
            }
        }
        return false;
    }


    public void checkSetDefaultDataLogin() throws IOException {
        File file = context.getFileStreamPath(IotConstant.FILE_ACCOUNT);
        if(!file.exists()){
            saveAccount(IotConstant.USER_ACCOUNT_DEFAULT, IotConstant.PASS_ACCOUNT_DEFAULT);
        }
    }




}
