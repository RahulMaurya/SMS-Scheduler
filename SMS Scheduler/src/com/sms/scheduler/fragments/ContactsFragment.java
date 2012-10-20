package com.sms.scheduler.fragments;

/*
 * This class grab the contacts from the phone along with the phone number of each contact
 * and the contact image and displays in the list format
 * It uses content provider with the URI ContactsContract.Contacts.CONTENT_URI for contacts name
 * CursorLoader optimizes the retrieval
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.TargetApi;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.sms.scheduler.activities.AddContact;
import com.sms.scheduler.activities.R;

@TargetApi(5)
public class ContactsFragment extends ListFragment{

	static final String DisplayName = "Display Name";
	static final String PhoneNumber = "Phone Number";
	static final String DisplayImage="Display Image";
	static final String pleaseRemoveThisvariableAsyouseethis = "test";
	 
	Uri contact_name_uri = ContactsContract.Contacts.CONTENT_URI;
	Uri contact_phone_uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	
	
	Cursor contact_list_cursor = null;
	SimpleAdapter contacts_adapter;
	List<HashMap<String, String>> contact_hash = new ArrayList<HashMap<String,String>>();
	
	public ContactsFragment(){
		Log.v("Contact Fragment" ,"In Constructor");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		long startTime=System.currentTimeMillis();;
		Log.v("Contact Fragment" ,"In Oncreate()"+startTime);
		getContactsFromPhone();
		
		String[] from = new String[]{DisplayName,PhoneNumber,DisplayImage};
		int to[] = new int[]{R.id.tv_add_contact_display_name,R.id.tv_add_contact_phone_number,R.id.iv_add_contact_display_image};

		
		contacts_adapter = new SimpleAdapter(getActivity().getBaseContext(),contact_hash,R.layout.display_contact_details,from,to);
		setListAdapter(contacts_adapter);
		Log.v("Time Taken",(System.currentTimeMillis()-startTime)+"");
	}

	
	/*This method gets the phone contact list*/
	
	private void getContactsFromPhone(){
		Log.v("Contact Fragment" ,"In method getContactsFromPhone()");
		
		CursorLoader cursorLoader = new CursorLoader(getActivity(), contact_name_uri, null, null, null, null);
		contact_list_cursor = cursorLoader.loadInBackground();	


		if (contact_list_cursor.moveToFirst()) {
			do{
				HashMap<String,String> mapString = new HashMap<String,String>();
				
				String contactID = contact_list_cursor.getString(contact_list_cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String contactDisplayName =contact_list_cursor.getString(contact_list_cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				Log.v("Content Providers", contactID + ", " + contactDisplayName);


				int hasPhone =
						contact_list_cursor.getInt(contact_list_cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

				if (hasPhone == 1) 
				{
					Cursor phoneCursor = getActivity().getContentResolver().query(contact_phone_uri, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID, null, null);
					//Cursor imageCursor = getActivity().getContentResolver().query(contact_name_uri,null,ContactsContract.Data.CONTACT_ID + "=" + contactID + " AND " + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,null);
					
					while (phoneCursor.moveToNext()) 
					{
						mapString.put(DisplayName,contactDisplayName );
						mapString.put(PhoneNumber, phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
						
						contact_hash.add(mapString);
						//Log.v(contactDisplayName,phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
					}

					phoneCursor.close();
				}


			} while (contact_list_cursor.moveToNext());


		}

		

	}
}
