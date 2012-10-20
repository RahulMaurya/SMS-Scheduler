package com.sms.scheduler.fragments;

/*
 * This class create the time picker dialog
 */

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sms.scheduler.activities.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	public static String TIME_SELECTED;
	int GET_DIALOG_CODE ;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		TextView tv_time = (TextView)getActivity().findViewById(R.id.tv_new_rl_scheduled_time);
		if(minute<10)
			tv_time.setText(hourOfDay +":0"+ minute);
		else
			tv_time.setText(hourOfDay +":"+ minute);
		TIME_SELECTED=tv_time.getText().toString();

	}

}
