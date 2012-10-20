package com.sms.scheduler.activities;
/*
 * This class is the basic Add Contact Activity which contains the fragments
 * 1)Contacts Fragment - for displaying the contact list
 * 2)Recent Fragment - for displaying the recently used contacts
 * 3)Groups Fragment - for displaying the groups
 * 
 * The fragments which will switched on user clicks
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.sms.scheduler.fragments.ContactsFragment;
import com.sms.scheduler.fragments.GroupFragment;
import com.sms.scheduler.fragments.RecentFragment;


public class AddContact extends FragmentActivity   {
	
	public static FragmentTransaction fragmentTransaction = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.v("Add Contact","In onCreate()");
		
		setContentView(R.layout.activity_add_contact);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add_contact, menu);
		return true;
	}

	public void showContacts(View view){
		Log.v("Add contact","In method show Contacts");
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();

		Fragment fragment = new ContactsFragment();
		fragmentTransaction.replace(R.id.ll_add_fragment_container, fragment);
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTransaction.commit();
		fragmentManager.executePendingTransactions();
		
		Log.v("Add contact","In method show Contacts after committing");
	}

	public void showRecent(View view){


		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();

		Fragment fragment = new RecentFragment();
		fragmentTransaction.replace(R.id.ll_add_fragment_container, fragment);
		fragmentTransaction.commit();
	}

	public void showGroups(View view){


		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();

		Fragment fragment = new GroupFragment();
		fragmentTransaction.replace(R.id.ll_add_fragment_container, fragment);
		fragmentTransaction.commit();
		
	}


}
