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
import com.sms.scheduler.database.ContactSetterAdapter;

@TargetApi(5)
public class ContactsFragment extends Fragment{

	
	ListView list;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("Contact Fragment" ,"In Oncreate()");
		new ContactReader(this.getActivity());
		list=(ListView)getActivity().findViewById(R.id.list);
		Log.v("Contact Fragment" ,"Setting Adapter");
		list.setAdapter(ContactSetterAdapter.adapter);
	
	}


}
