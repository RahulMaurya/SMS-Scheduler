package com.sms.scheduler.fragments;

/*
 * This class sets the List with contact details
 */


import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import com.sms.scheduler.activities.R;
import com.sms.scheduler.database.ContactReader;

@TargetApi(5)
public class ContactsFragment extends Fragment{

	
	ListView list;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("Contact Fragment" ,"In Oncreate()");
		new ContactReader(this.getActivity().getApplicationContext());
		list=(ListView)this.getActivity().findViewById(R.id.list);
		Log.v("ContactsFragment" ,"Setting Adapter");
		
		Log.v("ArrayListValues", ContactReader.contactList.toString());
		Log.v("ContactReader", ContactReader.adapter.isEmpty()+"");
		Log.v("ContactReader", ContactReader.adapter.getCount()+"");
		list.setAdapter(ContactReader.adapter);
	
	}


}
