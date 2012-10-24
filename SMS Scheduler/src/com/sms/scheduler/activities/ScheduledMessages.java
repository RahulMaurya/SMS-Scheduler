package com.sms.scheduler.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sms.scheduler.database.Contact;
import com.sms.scheduler.database.Message;
import com.sms.scheduler.database.MessageDataSource;

/*
 * This Class is the activity displaying list of scheduled messages in a vertical list.
 */
public class ScheduledMessages extends ListActivity  {

	//private static final String TAG = "Scheduled Messages";
	private static final String DATE_LABEL = "date";
	private static final String MONTH_LABEL = "month";
	private static final String YEAR_LABEL = "year";
	private static final String TIME_LABEL = "time";
	private static final String RECIPIENTS_LABEL = "recipients";
	private static final String BODY_LABEL = "body";
	private static final String TIME_LEFT_LABEL = "timeleft";
	private static final String TIME_LEFT_LABEL_COLOR = "timeLabelColor";

	public static Context ApplicationContext;

	MessageDataSource dataSource;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataSource = new MessageDataSource(this);
		dataSource.open();
		/*ApplicationContext = this.getApplicationContext();
		Intent i = new Intent(this, com.sms.scheduler.database.ContactReader.class);
		startService(i);*/
		

		List<Message> messageList = dataSource.getAllMessages();        


		List<HashMap<String,String>> mHashMapList = new ArrayList<HashMap<String,String>>();
		String[] from = new String[]
				{
				DATE_LABEL,
				MONTH_LABEL,
				YEAR_LABEL,
				TIME_LABEL,
				RECIPIENTS_LABEL,
				BODY_LABEL,
				TIME_LEFT_LABEL,
				TIME_LEFT_LABEL_COLOR
				};

		int[] to   = new int[]
				{
				R.id.tv_sch_date,
				R.id.tv_sch_month,
				R.id.tv_sch_year,
				R.id.tv_sch_time,
				R.id.tv_sch_recepient_container,
				R.id.tv_sch_msg_body,
				R.id.tv_sch_time_left,
				R.id.tv_sch_color_label
				};


		//Iterate through all messages and add each message details in HashMap and add then add to ArrayList of HashMap.
		for(Message message: messageList){
			HashMap<String,String> map = new HashMap<String,String>();


			/*Prepare Recipients String to be put in TO: field*/
			ArrayList<Contact> recipients = message.getRecipients();
			String recipientsString="";
			if(recipients != null){
				for(int i1=recipients.size()-1;i1>=0;i1--){
					recipientsString = recipientsString +" "+ recipients.get(i1).getName();
					if(i1!=0){
						recipientsString = recipientsString + ",";
					}
				}
			}
			/*RecipientsList prepared.*/


			map.put(DATE_LABEL, Integer.toString(message.getDate()));
			map.put(MONTH_LABEL, message.getMonth());
			map.put(YEAR_LABEL, Integer.toString(message.getYear()));
			map.put(TIME_LABEL, message.getHour()+":"+Integer.toString(message.getMinute()));        	        
			map.put(RECIPIENTS_LABEL, recipientsString);        	
			map.put(BODY_LABEL, message.getBody());
			map.put(TIME_LEFT_LABEL,message.getTimeLeft(message.getTime()));

			//Setting color before adding its value to hashmap
			message.setLabelColor(message.getTime()); 
			map.put(TIME_LEFT_LABEL_COLOR, String.valueOf(message.getColor()));

			mHashMapList.add(map);        	
		}

		SimpleAdapter mAdapter = new SimpleAdapter(this,mHashMapList,R.layout.scheduled_message_list_item,from,to);


		/*Override functionality of adapter. Now making it to set background color for colorLabel instead of text*/
		mAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

			public boolean setViewValue(View view, Object data,String textRepresentation) {
				int v=view.getId();
				if(v==R.id.tv_sch_color_label){
					view.setBackgroundColor(Integer.parseInt(data.toString()));
				}else{
					TextView TV=(TextView) view;
					TV.setText(data.toString());
				}
				return true;
			}
		});
		/*Code Ends*/

		setListAdapter(mAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_sch, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent testIntent = new Intent(this, Test.class);
			testIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);                
			startActivity(testIntent);

			return true;
		case R.id.menu_sch_new_message:
			Intent newIntent = new Intent("com.sms.scheduler.activities.New");
			//newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);            	
			startActivity(newIntent);

			return true;
		case R.id.menu_sch_delete:
			toast(item.getTitle().toString());
			return true;
		case R.id.menu_sch_sort:
			toast(item.getTitle().toString());
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	//Test method to show Toast Messages
	private void toast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}




}
