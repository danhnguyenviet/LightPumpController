package com.danh.iot.com.danh.iot.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.danh.iot.R;

import java.util.ArrayList;

/**
 * Created by NPhat on 1/3/2016.
 */
public class AdapterSetting_copy extends BaseAdapter{
    private Activity context;
    private int resource;
    private ArrayList<String> arrayList;

    public AdapterSetting_copy(Activity context, int resource, ArrayList arrayList){
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_list_setting,null);

            holder.textView = (TextView)convertView.findViewById(R.id.tv_item_setting);
            holder.editText = (EditText)convertView.findViewById(R.id.ed_item_setting);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.textView.setText("FP " + position);
        holder.editText.setText(arrayList.get(position));

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arrayList.add(position,s.toString());
            }
        });



        return convertView;
    }

    class ViewHolder{
        TextView textView;
        EditText editText;
    }


}
