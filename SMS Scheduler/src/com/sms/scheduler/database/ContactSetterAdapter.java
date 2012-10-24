package com.sms.scheduler.database;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sms.scheduler.activities.R;

public class ContactSetterAdapter extends BaseAdapter {

	public static ContactSetterAdapter adapter;
	
	private Activity  activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater;
	private ContactImageLoader image_loader;

	public ContactSetterAdapter	(Activity activity, ArrayList<HashMap<String, String>> data) {
		this.activity = activity;
		this.data=data;
		Log.v("ContactSetterAdapter", "datasize is "+data.size());
		ContactSetterAdapter.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.image_loader=new ContactImageLoader(activity.getApplicationContext());
		
	}
	
	
	public int getCount() {
		Log.v("ContactSetterAdapter","in GetCount"+data.size());
		return data.size();
	}

	public Object getItem(int arg0) {
		Log.v("ContactSetterAdapter","in getItem"+data+":"+arg0);
		return data;
	}

	public long getItemId(int position) {
		Log.v("ContactSetterAdapter","in getItemId"+position);
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		Log.v("ContactSetterAdapter","in getView");
		if(convertView == null){
			Log.v("ContactSetterAdapter", "convert view is null");
			convertView = inflater.inflate(R.layout.display_contact_details, null);
		}
		Log.v("ContactSetterAdapter", "convert view is not null");
		TextView contactName =(TextView)convertView.findViewById(R.id.tv_add_contact_display_name) ;
		TextView contactNumber =(TextView)convertView.findViewById(R.id.tv_add_contact_phone_number);
		ImageView contactImage =(ImageView)convertView.findViewById(R.id.iv_add_contact_display_image); 
		
		HashMap<String, String> contact = new HashMap<String, String>();
		contact = data.get(position);
		
		contactName.setText(contact.get(ContactReader.KEY_NAME));
		contactNumber.setText(contact.get(ContactReader.KEY_PHONE_NUMBER));
		image_loader.displayImage(contact.get(ContactReader.KEY_CONTACT_THUMB),contactImage);
		
		return convertView;
	}

}
