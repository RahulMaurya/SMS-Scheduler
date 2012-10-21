package com.sms.scheduler.fragments;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.SimpleAdapter;

import com.sms.scheduler.activities.R;



public class RecentFragment extends ListFragment {

	static final String DisplayName = "Display Name";
	static final String PhoneNumber = "Phone Number";
	static final String DisplayImage="Display Image";

	List<HashMap<String, String>> contact_hash = new ArrayList<HashMap<String,String>>();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] from = new String[]{DisplayName,PhoneNumber,DisplayImage};
		int to[] = new int[]{R.id.tv_add_contact_display_name,R.id.tv_add_contact_phone_number,R.id.iv_add_contact_display_image};

		
		for(int i=0;i<10;i++){
			
			HashMap<String,String> mapString = new HashMap<String,String>();
			mapString.put(DisplayName, "name"+i);
			mapString.put(PhoneNumber, "phone"+i);
			contact_hash.add(mapString);
			
		}
		SimpleAdapter test_adapter = new SimpleAdapter(getActivity().getBaseContext(),contact_hash,R.layout.display_contact_details,from,to);
		setListAdapter(test_adapter);
	}


}
