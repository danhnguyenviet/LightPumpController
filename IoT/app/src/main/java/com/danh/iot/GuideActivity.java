package com.danh.iot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by NPhat on 14/3/2016.
 */
public class GuideActivity extends AppCompatActivity {
    TextView tvContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        tvContent = (TextView)findViewById(R.id.tv_guide_content);
        tvContent.setText(Html.fromHtml(setContent()));

    }

    protected String setContent(){
        return new String(
                "<b>[Change password]</b><br/>" +
                        "- Main screen, touch menu (Right corner of the screen), touch <b>Change password</b><br/>" +
                        "- Enter <b>Current password</b>, <b>New password</b> and <b>Confirm password</b><br/>" +
                        "- Touch <b>Change password</b> button to save or <b>Cancel</b> button to exit<br/>" +

                        "<b><br/>[Settings]</b><br/>" +
                        "- Main screen, touch <b>SETTINGS</b> button<br/>" +
                        "- Change <b>Port</b>, list <b>F</b><br/>" +
                        "- Touch <b>Save</b> button to save settings or <b>Cancel</b> button to exit<br/>"
        );
    }
}
