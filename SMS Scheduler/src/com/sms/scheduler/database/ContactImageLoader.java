package com.sms.scheduler.database;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.widget.ImageView;

public class ContactImageLoader {

	private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

	Context context;

	public ContactImageLoader(Context applicationContext) {
		context = applicationContext;
	}


	public void displayImage(String id, ImageView contactImage) {
		imageViews.put(contactImage, id);

		Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.parseLong(id));
		Uri photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY);
		Cursor cursor = context.getContentResolver().query(photoUri,new String[] {Contacts.Photo.PHOTO}, null, null, null);

		if (cursor == null) {
			Log.v("ContactImageLoader", "No Image Found");
		}
		try {
			if (cursor.moveToFirst()) {
				byte[] data = cursor.getBlob(0);
				if (data != null) {
					Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
					contactImage.setImageBitmap(image);
				}
				else
					Log.v("ContactImageLoader","Error in setting Image");
			}

		} finally {
			cursor.close();
		}
	}
}
