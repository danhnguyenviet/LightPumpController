package com.danh.iot;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.danh.iot.R;

/**
 * Created by Danh on 3/14/2016.
 */
public class TabhostSystemActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        loadTabs();
    }

    public void loadTabs()
    {
        //Lấy Tabhost id ra trước (cái này của built - in android
        final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
        //gọi lệnh setup
//        tab.setup();
        TabHost.TabSpec spec;
        //Tạo tab1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Light");
        tab.addTab(spec);
        //Tạo tab2
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Pump");
        tab.addTab(spec);
        //Thiết lập tab mặc định được chọn ban đầu là tab 0
        tab.setCurrentTab(0);
        //Ở đây Tôi để sự kiện này để các bạn tùy xử lý
        //Ví dụ tab1 chưa nhập thông tin xong mà lại qua tab 2 thì báo...
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String arg0) {
                String s="Tab tag ="+arg0 +"; index ="+
                        tab.getCurrentTab();
                Toast.makeText(getApplicationContext(),
                        s, Toast.LENGTH_SHORT).show();}
        });
    }
}
