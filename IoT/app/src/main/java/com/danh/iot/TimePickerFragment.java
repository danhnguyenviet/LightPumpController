package com.danh.iot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Danh on 2/23/2016.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        /*
            public constructor.....
            TimePickerDialog(Context context, int theme,
             TimePickerDialog.OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView)

            The 'theme' parameter allow us to specify the theme of TimePickerDialog

            .......List of Themes.......
            THEME_DEVICE_DEFAULT_DARK
            THEME_DEVICE_DEFAULT_LIGHT
            THEME_HOLO_DARK
            THEME_HOLO_LIGHT
            THEME_TRADITIONAL

         */
        TimePickerDialog tpd = new TimePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK
                ,this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        //You can set a simple text title for TimePickerDialog
        //tpd.setTitle("Title Of Time Picker Dialog");

        /*.........Set a custom title for picker........*/
        TextView tvTitle = new TextView(getActivity());
        tvTitle.setText("Set Time");
//        tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
        tvTitle.setPadding(5, 3, 5, 3);
        tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        tpd.setCustomTitle(tvTitle);
        /*.........End custom title section........*/

        return tpd;
    }

    @Override
    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        TextClock tc0 = (TextClock) getActivity().findViewById(R.id.tcStartedTime);
        TextClock tc1 = (TextClock) getActivity().findViewById(R.id.tcStoppedTime);
        //Set a message for user

        String format = "";

        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = " AM";
        }
        else if (hourOfDay == 12) {
            format = " PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = " PM";
        } else {
            format = " AM";
        }

        if (SystemActivity.numOfTextClock.equals(0)) {
            tc0.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + format);
        } else if (SystemActivity.numOfTextClock.equals(1)) {
            tc1.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + format);
        }

    }
}
