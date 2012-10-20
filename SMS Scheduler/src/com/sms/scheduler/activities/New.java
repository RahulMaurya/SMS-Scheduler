package com.sms.scheduler.activities;

/*
 * This class is used for creating a new message and provides the option for following
 * 1)Save in Drafts
 * 2)Save as Template
 * 
 * The new messages created will be scheduled through this class only
 */


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sms.scheduler.fragments.DatePickerFragment;
import com.sms.scheduler.fragments.TimePickerFragment;


public class New extends FragmentActivity {

	int GET_CONTACT = 1;//Test variable
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);

		EditText to = (EditText)findViewById(R.id.et_new_to);
		to.setInputType(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_new, menu);
		return true;
	}

	
	/*This Method is for saving data during change in orientation*/
	public void onResume(){
		super.onResume();
		
		LinearLayout scheduled_details_container = (LinearLayout) findViewById(R.id.ll_new_schedule_details); 
		
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE )
			scheduled_details_container.setOrientation(LinearLayout.HORIZONTAL);
			
		TextView tv_date = (TextView)findViewById(R.id.tv_new_rl_scheduled_date);
		TextView tv_time = (TextView)findViewById(R.id.tv_new_rl_scheduled_time);
		if (DatePickerFragment.DATE_SELECTED == null)
			tv_date.setText("Set Date");
		else 
			tv_date.setText(DatePickerFragment.DATE_SELECTED);

		if (TimePickerFragment.TIME_SELECTED == null)
			tv_time.setText("Set Time");
		else
			tv_time.setText(TimePickerFragment.TIME_SELECTED);


	}

	/*This method is called when the user adds recipients and it starts the activity Add Contact*/
	public void grabContacts(View view){

		Intent grabContacts = new Intent("com.sms.scheduler.actvities.AddContact");
		startActivityForResult(grabContacts, this.GET_CONTACT);
	}

	/*Date Picker is started via this method*/
	public void datePicker(View view){
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "DatePicker");


	}

	/*Time Picked is started via this method*/
	public void timePicker(View view){

		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "TimePicker");

	}
}
