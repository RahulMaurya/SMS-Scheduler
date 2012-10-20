package com.sms.scheduler.fragments;

/*
 * This fragment shows the recipients selected by the user
 */

import com.sms.scheduler.activities.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipientFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
		
		return inflater.inflate(R.layout.recipients, container);
	}
	
}
