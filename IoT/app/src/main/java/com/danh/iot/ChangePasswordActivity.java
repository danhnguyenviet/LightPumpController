package com.danh.iot;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danh.iot.com.danh.iot.database.AccountIOT;

import java.io.IOException;

/**
 * Created by NPhat on 10/3/2016.
 */
public class ChangePasswordActivity extends AppCompatActivity {
    EditText edCurrentPass, edNewPass, edConfirmPass;
    Button btnChange, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password);


        edCurrentPass = (EditText)findViewById(R.id.ed_change_password_old);
        edNewPass = (EditText)findViewById(R.id.ed_change_password_new);
        edConfirmPass = (EditText)findViewById(R.id.ed_change_password_renew);

        btnChange = (Button)findViewById(R.id.btn_change_password_change);
        btnCancel = (Button)findViewById(R.id.btn_change_password_cancel);

        //Set event for button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = edCurrentPass.getText().toString();
                String newPassword = edNewPass.getText().toString();
                String confirmPassword = edConfirmPass.getText().toString();

                if(currentPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")){
                    if(currentPassword.equals("")){
                        edCurrentPass.requestFocus();
                        Toast.makeText(ChangePasswordActivity.this,"Current password is empty",Toast.LENGTH_SHORT).show();
                    }else if(newPassword.equals("")){
                        edNewPass.requestFocus();
                        Toast.makeText(ChangePasswordActivity.this,"New password is empty",Toast.LENGTH_SHORT).show();
                    }else if(confirmPassword.equals("")){
                        edConfirmPass.requestFocus();
                        Toast.makeText(ChangePasswordActivity.this,"Confirm password is empty",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(newPassword.equals(confirmPassword) ){
                        changePassword(newPassword,currentPassword);
                    }else{
                        edConfirmPass.requestFocus();
                        Toast.makeText(ChangePasswordActivity.this,"Confirm password do not match",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    /**
     * Call thread change password
     * @param newPassword
     * @param currentPassword
     */
    public void changePassword(final String newPassword, final String currentPassword){
        new Thread(){
            @Override
            public void run() {
                super.run();
                AccountIOT accountIOT = new AccountIOT(ChangePasswordActivity.this);
                try {
                    boolean result = accountIOT.checkPassword(currentPassword, newPassword);

                    //Create data send to MainActivity
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("changed", result);

                    Message message = Message.obtain();
                    message.setData(bundle);

                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.getData().getBoolean("changed",false) == true){
                Intent intent = getIntent();
                intent.putExtra("changed",true);

                //set result and return MainActivity
                setResult(IotConstant.RESULT_CODE_ACTIVITY_CHANGE_PASSWORD, intent);
                finish();
            }else{
                Toast.makeText(ChangePasswordActivity.this,"Change password fail...",Toast.LENGTH_SHORT).show();
            }
        }
    };



}
