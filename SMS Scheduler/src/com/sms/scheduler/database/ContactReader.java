package com.sms.scheduler.database;
/*This class consults the phones data and gets all the contacts and other information from 
 * the different tables
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.sms.scheduler.activities.ScheduledMessages;


public class ContactReader/* extends IntentService*/{

	static final String KEY_NAME = "contact name";
	static final String KEY_PHONE_NUMBER = "phone number";
	static final String KEY_PHONE_TYPE = "phone type";
	static final String KEY_CONTACT_THUMB = "contact image";

	static Uri contact_name_uri = ContactsContract.Contacts.CONTENT_URI;
	static Uri contact_phone_uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

	static ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	static Cursor contact_list_cursor;
	static ContactSetterAdapter contact_adapter;
	static Activity activity;
	public ContactReader(Activity activity){
		Log.v("ContactReader" ,"In Constructor");		
		this.activity = activity;
		ContactReader.readIntoCursor();
	}
	/*public ContactReader() {
		super("IntentService");

	}

	@Override
	protected void onHandleIntent(Intent arg0) {

		
		readIntoCursor();

	}*/

	public static void readIntoCursor(){

		CursorLoader cursorLoader = new CursorLoader(activity, contact_name_uri, null, null, null, null);
		contact_list_cursor = cursorLoader.loadInBackground();

		if (contact_list_cursor.moveToFirst()) {
			do{
				String contactID = contact_list_cursor.getString(contact_list_cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String contactDisplayName =contact_list_cursor.getString(contact_list_cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				HashMap<String, String> map = new HashMap<String, String>();

				int hasPhone = contact_list_cursor.getInt(contact_list_cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

				if (hasPhone == 1) 
				{
					Cursor phoneCursor = activity.getContentResolver().query(contact_phone_uri, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID, null, null);
					while (phoneCursor.moveToNext()) {

						String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						map.put(KEY_NAME, contactDisplayName);
						map.put(KEY_PHONE_NUMBER, phoneNumber);
						map.put(KEY_CONTACT_THUMB,contactID);//contact ID is passed to get Image thumb
					
						contactList.add(map);
					}
					
					phoneCursor.close();
				}
				
			}while (contact_list_cursor.moveToNext());
		}

	
	contact_list_cursor.close();
	
	ContactSetterAdapter.adapter = new ContactSetterAdapter(activity, contactList);
	}
}
