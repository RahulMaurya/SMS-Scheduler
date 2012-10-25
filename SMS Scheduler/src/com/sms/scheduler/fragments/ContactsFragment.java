package com.sms.scheduler.fragments;

/*
 * This class sets the List with contact details
 */


import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sms.scheduler.activities.R;
import com.sms.scheduler.database.ContactImageLoader;
import com.sms.scheduler.database.ContactReader;

@TargetApi(5)
public class ContactsFragment extends ListFragment{


	static final String KEY_NAME = "contact name";
	static final String KEY_PHONE_NUMBER = "phone number";
	static final String KEY_PHONE_TYPE = "phone type";
	static final String KEY_CONTACT_THUMB = "contact image";
	static SimpleAdapter adapter;
	ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();;
	String[] from = new String[]{
			KEY_NAME,
			KEY_PHONE_NUMBER,
			KEY_CONTACT_THUMB
	};
	int[] to   = new int[]{
			R.id.tv_add_contact_display_name,
			R.id.tv_add_contact_phone_number,
			R.id.iv_add_contact_display_image,

	};


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("Contact Fragment" ,"In Oncreate()");
		contactList = new ContactReader().readIntoCursor(this.getActivity().getApplicationContext());
		Log.v("Contact List Returned", ""+contactList.toString());

		//	ListView list;
		//	list=(ListView)getActivity().findViewById(R.id.list);

		adapter = new SimpleAdapter(this.getActivity().getApplicationContext(), contactList, R.layout.display_contact_details, from, to);
		adapter.setViewBinder(new SimpleAdapter.ViewBinder() {

			public boolean setViewValue(View view, Object data,	String textRepresentation) {
				Log.v("ContactReader","In view Binder");
				int v = view.getId();
				if(v == R.id.iv_add_contact_display_image ){
					ImageView iv = (ImageView) view;
					Bitmap ret_image = new ContactImageLoader(getActivity().getApplicationContext()).displayImage(data.toString());
					if (ret_image == null){
						Log.v("Bitmap", "Nothing found");
						iv.setBackgroundColor(255);
					}
					else{
						Log.v("Bitmap", "Bitmap set");
						iv.setImageBitmap(ret_image);
				
					}
				}
				return false;
			}
		});
		Log.v("ContactReader", adapter.getCount()+"");
		this.setListAdapter(adapter);	

	}


}
