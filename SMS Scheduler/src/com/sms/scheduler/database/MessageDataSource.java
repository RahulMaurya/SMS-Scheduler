package com.sms.scheduler.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/*
 * This class acts as a Data Source for inserting message in database, getting messages from database and
 * provide functions to get any data related to message from database and pass this data to calling activity.
 */
public class MessageDataSource {

	private static final String TAG = "Message Data Source";
	private SQLiteDatabase database;
	private MySQLiteOpenHelper dbHelper;
	private String[] allColumns = { MySQLiteOpenHelper.COLUMN_ID,
									MySQLiteOpenHelper.COLUMN_RECEPIENTS,
									MySQLiteOpenHelper.COLUMN_BODY,
									"datetime("+MySQLiteOpenHelper.COLUMN_TIME+")",
									MySQLiteOpenHelper.COLUMN_TYPE};
	
	
	public MessageDataSource(Context context) {
	    dbHelper = new MySQLiteOpenHelper(context);
	}
	
	
	public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
	    dbHelper.close();
	}
	
	/*
	 * Function to add a new message entry in database and 
	 * return the added message on successful addition of message in database else it return null.
	 * So where ever this function is used a null check should be made to avoid any null pointer exception.
	 * @param recipients List of Contacts to which message is scheduled
	 * @param body String representing body of message
	 * @param cal  Calendar object having the details regarding scheduled time
	 * @param type String represents type of message whether Scheduled, Sent or Draft
	 */
	public Message addMessage(ArrayList<Contact> recipients, String body, Calendar cal, String type){
		SimpleDateFormat dateFormat = new SimpleDateFormat(Message.MESSAGE_DATE_FORMAT);
		String recipientString = "";
		
		if(recipients!=null){
			for(Contact contact: recipients){
				recipientString = recipientString+contact.getName()+"\n"+contact.getNumber()+",";
			}
		}
		ContentValues values = new ContentValues();
	    values.put(MySQLiteOpenHelper.COLUMN_BODY, body);
	    values.put(MySQLiteOpenHelper.COLUMN_TYPE, type);
	    values.put(MySQLiteOpenHelper.COLUMN_TIME, dateFormat.format(cal.getTime()));
	    values.put(MySQLiteOpenHelper.COLUMN_RECEPIENTS, recipientString);
	    	    
	    long insertId = database.insert(MySQLiteOpenHelper.TABLE_NAME, null,values);
	    
	    if(insertId==-1){
	    	return null;
	    }else{
		    Cursor cursor = database.query(
		    		MySQLiteOpenHelper.TABLE_NAME,
		    		allColumns, 
		    		MySQLiteOpenHelper.COLUMN_ID + " = " + insertId, 		    		
		    		null,null, null, null);	    
		    cursor.moveToFirst();
		    Message newMessage= cursorToMessage(cursor);
		    cursor.close();
		    return newMessage;
	    }
	}
	
	public void deleteMessage(Message message) {
	    long id = message.getId();
	    System.out.println("Message deleted with id: " + id);
	    database.delete(MySQLiteOpenHelper.TABLE_NAME, MySQLiteOpenHelper.COLUMN_ID
	        + " = " + id, null);
	}
	/*
	 * This function returns List of all the messages from database.
	 */
	public List<Message> getAllMessages() {
	  List<Message> messages = new ArrayList<Message>();
	  Cursor cursor = database.query(MySQLiteOpenHelper.TABLE_NAME,
	        allColumns, null, null, null, null, null);
	
	  cursor.moveToFirst();
	  while (!cursor.isAfterLast()) {
	    Message message = cursorToMessage(cursor);
	    messages.add(message);
	    cursor.moveToNext();
	  }
	  // Make sure to close the cursor
	  cursor.close();
	  return messages;
	}	
	/*
	 * This function accept cursor as parameter and returns a Message object corresponding to the row in message table in database which 
	 * the cursor is pointing to.
	 */
	private Message cursorToMessage(Cursor cursor) {
		Message message = new Message();
		Calendar cal = Calendar.getInstance();
		ArrayList<Contact> contactList = new ArrayList<Contact>();
		String contactString ;
		
		try {			
			java.util.Date date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH)
				.parse(cursor.getString(cursor.getColumnIndex("datetime("+MySQLiteOpenHelper.COLUMN_TIME+")")));
			
			cal.setTime(date);						
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		contactString = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_RECEPIENTS));
		StringTokenizer mStringTokenizer = new StringTokenizer(contactString,",");
		while(mStringTokenizer.hasMoreTokens()){
			String token = mStringTokenizer.nextToken();
			StringTokenizer newLineTokenizer = new StringTokenizer(token,"\n");
			Contact contact = new Contact();
			contact.setName(newLineTokenizer.nextToken());
			contact.setNumber(newLineTokenizer.nextToken());
			//Log.d(TAG,"Name: "+contact.getName()+" Number: "+contact.getNumber());
			contactList.add(contact);			
		}
		
		message.setId(cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_ID)));
		message.setBody(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_BODY)));
		message.setType(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_TYPE)));
		message.setDate(cal.getTime());
		message.setRecipients(contactList);
		
		return message;
	}
	
	
}
