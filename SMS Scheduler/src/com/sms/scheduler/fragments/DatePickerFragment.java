package com.sms.scheduler.fragments;

import java.util.Calendar;
import com.sms.scheduler.activities.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	public static String DATE_SELECTED;
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DAY_OF_MONTH);
        

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, date);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
    	TextView tv_date = (TextView)getActivity().findViewById(R.id.tv_new_rl_scheduled_date);
    	tv_date.setText(day+"/"+month+"/"+year);
    	
    	DATE_SELECTED = tv_date.getText().toString();
    }
}
