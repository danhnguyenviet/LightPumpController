package com.danh.iot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.danh.iot.com.danh.iot.thread.ThreadLogin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NPhat on 8/3/2016.
 */
public class LoginActivity extends AppCompatActivity {
    EditText edUsername, edPassword;
    TextView tvBtnLogin;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = (EditText)findViewById(R.id.ed_login_username);
        edPassword = (EditText)findViewById(R.id.ed_login_password);
        tvBtnLogin = (TextView)findViewById(R.id.tv_login_submit);

        tvBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValueInput()){
                    progressDialog = ProgressDialog.show(LoginActivity.this,"Login","Waiting...");
                    ThreadLogin threadLogin = new ThreadLogin(edUsername.getText().toString(),edPassword.getText().toString(),handler);
                    threadLogin.start();
                }

            }
        });

    }


    /**
     *Check username and password empty
     * @return true if inputs value
     */
    public boolean checkValueInput(){
        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();

        if(username.equals("")){
            edUsername.requestFocus();
            return false;
        }
        if(password.equals("")){
            edPassword.requestFocus();
            return false;
        }
        return true;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                progressDialog.dismiss();
                readResponse(msg.getData().getString("data").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Parse result to jsonObject and go to MainActivity if login ok. if login fail is send Toast
     * @param data
     * @throws JSONException
     */
    public void readResponse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);

        String status = jsonObject.getString("status");
        String result = jsonObject.getString("data");

        if(status.equals("1")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(LoginActivity.this,result,Toast.LENGTH_SHORT).show();
        }
    }
}
