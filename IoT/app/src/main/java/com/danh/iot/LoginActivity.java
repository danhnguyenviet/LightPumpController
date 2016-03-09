package com.danh.iot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
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
    CheckBox cbRemember;

    ProgressDialog progressDialog;

    String sharedPre = "rememberLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = (EditText)findViewById(R.id.ed_login_username);
        edPassword = (EditText)findViewById(R.id.ed_login_password);
        tvBtnLogin = (TextView)findViewById(R.id.tv_login_submit);
        cbRemember = (CheckBox)findViewById(R.id.cb_login_remember);

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

    @Override
    protected void onResume() {
        super.onResume();
        readRememberLogin();

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveRememberLogin();
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

    /**
     * Save status of Login
     */
    public void saveRememberLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPre, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();
        Boolean cb = cbRemember.isChecked();

        if(!cb){
            editor.clear();
        }else{
            editor.putString("username",username);
//            editor.putString("password",password);
            editor.putBoolean("checkbox", cb);
        }
        editor.commit();
    }

    /**
     * Read status of Login
     */
    public void readRememberLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPre,MODE_PRIVATE);

        boolean cb = sharedPreferences.getBoolean("checkbox",false);
        if(cb){
            edUsername.setText(sharedPreferences.getString("usename",""));
        }
        cbRemember.setChecked(cb);
    }
}
