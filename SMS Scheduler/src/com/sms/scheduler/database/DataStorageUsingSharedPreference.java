package com.sms.scheduler.database;

/*
 * This class uses the context of application and file name as the  parameter to create a 
 * preference file.
 * This class can also be used to edit and retrieve the values too. 
 */


import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class DataStorageUsingSharedPreference {

	SharedPreferences preferences;

	public DataStorageUsingSharedPreference(Context context,String FileName){

		this.preferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
	}

	public DataStorageUsingSharedPreference(Context context,String FileName,int MODE){

		this.preferences = context.getSharedPreferences(FileName, MODE);
	}


	public void editPreferences(String key ,String value){

		SharedPreferences.Editor preferences_editor = preferences.edit();
		preferences_editor.putString(key,value);
		preferences_editor.commit();
	}

	public Map<String, String> showPreferences(){
		@SuppressWarnings("unchecked")
		Map<String, String> all_values = (Map<String, String>) preferences.getAll();
		return all_values;
	}

}
