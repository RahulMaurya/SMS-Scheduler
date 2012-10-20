package com.sms.scheduler.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.graphics.Color;
import android.util.Log;
/*
 * This Class act as a message entity, a message in the application is handled using this class.
 * It contains attribute of a message as variables and getter and setter method for each attribute.
 */
public class Message {
	public static final String TAG = "Message";
	public static final int MESSAGE_ID_MIN=-1;
	public static final String MESSAGE_TYPE_SCHEDULED="scheduled";
	public static final String MESSAGE_TYPE_SENT="sent";
	public static final String MESSAGE_TYPE_DRAFT="draft";
	public static final String MESSAGE_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	private static final long MILLISECONDS_IN_MINUTE = 1000 * 60;
	private static final long MILLISECONDS_IN_HOUR = MILLISECONDS_IN_MINUTE * 60;	
	private static final long MILLISECONDS_IN_DAY = MILLISECONDS_IN_HOUR * 24;
	private static final long MILLISECONDS_IN_WEEK = MILLISECONDS_IN_DAY * 7;
	private static final long MILLISECONDS_IN_MONTH = MILLISECONDS_IN_DAY * 31;
	private static final long MILLISECONDS_IN_YEAR = MILLISECONDS_IN_MONTH * 12;
	
	private static final int LESS_THAN_TWELVE_HOURS_COLOR = Color.parseColor("#CC0000");
	private static final int LESS_THAN_TWO_DAYS_COLOR = Color.parseColor("#FF8800");
	private static final int LESS_THAN_MONTH_COLOR = Color.parseColor("#669900");
	private static final int MORE_THAN_MONTH_COLOR = Color.parseColor("#333333");
	private static final int DEFAULT_LABEL_COLOR = Color.parseColor("#000000");
	
	private int id;
	private String body;
	private ArrayList<Contact> recipientList;
	private Calendar cal;
	private String type;
	private int color;
	/*
	 * Default Constructor for class. Attributes initialised to default values.
	 */
	Message(){
		id = MESSAGE_ID_MIN;
		body = "";
		recipientList = null;
		cal=null;
		type="";		
		setColor(DEFAULT_LABEL_COLOR);
	}
	
	/*
	 * Constructor to initialise Message object with attributes.
	 */
	@SuppressWarnings("unchecked")
	Message(int id, String body,ArrayList<Contact> list, Date date,String type){
		this.id = id;
		this.body = body;
		if(list!=null)
			this.recipientList =  (ArrayList<Contact>) list.clone();
		cal = Calendar.getInstance();
		cal.setTime(date);
		this.type = type;
		this.setColor(DEFAULT_LABEL_COLOR);
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getBody(){
		return this.body;
	}
	
	public ArrayList<Contact> getRecipients(){
		return this.recipientList;
	}
	
	
	
	/*
	 * Return month name in string format. Eg. Jan, Feb, Mar etc.
	 */
	public String getMonth(){
		SimpleDateFormat month_date = new SimpleDateFormat("MMM");
		if(cal != null){
			String month_name = month_date.format(cal.getTime());
			return month_name;
		}else{
			return null;
		}
			
	}
	
	/*
	 * Return date of scheduled time. Anything from 1 to 31
	 */
	public int getDate(){
		if(cal != null){
			return cal.get(Calendar.DATE);
		}else{
			return -1;
		}
	}
	
	/*
	 * Return year of scheduled time.
	 */
	public int getYear(){
		if(cal != null){
			return cal.get(Calendar.YEAR);
		}else{
			return  -1;			
		}
	}
		
	/*
	 * Return hour of scheduled time.
	 */
	public int getHour(){
		if(cal != null){
			return cal.get(Calendar.HOUR);
		}else{
			return  -1;			
		}
	}
	/*
	 * Return minute of scheduled time.
	 */
	public int getMinute(){
		if(cal != null){
			return cal.get(Calendar.MINUTE);
		}else{
			return  -1;			
		}		
	}
	
	/*
	 * Return Date Object of time related to message.
	 */
	public Date getTime(){	
		return cal.getTime();
	}
	
	
	
	/*
	 * Returns type of message whether scheduled, sent or draft message
	 */
	public String getType(){
		return this.type;		
	}
	
	/*
	 * Returns count of contacts added in recipient list and -1 if recipientList is null.
	 */
	public int getRecipientsCount(){
		if(recipientList != null){
			return recipientList.size();
		}
		return -1;
	}
	
	public int getColor() {
		return color;
	}
	
	
	/*
	 * Set id if greater than minimum value for id (-1) else set id to minimum value
	 */
	public void setId(int id){		
		this.id = (id <= MESSAGE_ID_MIN) ? MESSAGE_ID_MIN:id;		
	}
	
	public void setBody(String body){
		this.body=body;
	}
	
	/*
	 * Check if contact passed is not null and then add the contact to recipientList.
	 * If recipientList is null then create a new list and add the contact.
	 * Returns True if contact is successfully added to list
	 * else returns False.
	 */
	public void setRecipients(ArrayList<Contact> list){
		if(list!=null){
			if(recipientList == null){
				recipientList = new ArrayList<Contact>();
			}
			for(Contact contact: list){
				recipientList.add(contact);
			}
		}
	}
	
	public void setDate(Date date){
		if(cal == null){
			cal = Calendar.getInstance();
		}
		cal.setTime(date);
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	
	public void setColor(int color) {
		this.color = color;
	}
	
	/**
     * Calculates the number of days between start and end dates, taking
     * into consideration leap years, year boundaries etc.
     *
     * @param start the start date
     * @param end the end date, must be later than the start date
     * @return the number of days between the start and end dates
     */
    public long countDaysBetween(Date start, Date end) {
        if (end.before(start)) {
            return -1;
        }

        //reset all hours minutes and seconds to zero on start date
        Calendar startCal = GregorianCalendar.getInstance();
        startCal.setTime(start);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        long startTime = startCal.getTimeInMillis();

        //reset all hours minutes and seconds to zero on end date
        Calendar endCal = GregorianCalendar.getInstance();
        endCal.setTime(end);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        long endTime = endCal.getTimeInMillis();

        return endTime - startTime / MILLISECONDS_IN_DAY;
    }
    
    
    /**
     * Prepare string specifying time left in sending the scheduled message,
     * according to time left string is prepared as 3 days left or 4 hrs left or 3 minutes left.
     * 
     * @param date the scheduled date
     * @return String specifying amount of time left in sending SMS
     */
    public String getTimeLeft(Date date){
    	Calendar scheduledCal = GregorianCalendar.getInstance();
    	scheduledCal.setTime(date);
        
    	long timeLeftInMillis = scheduledCal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
    	
    	
    	if(timeLeftInMillis > MILLISECONDS_IN_YEAR){
    		 
    		return "More than an year left";
    		
    	}else if(timeLeftInMillis > MILLISECONDS_IN_MONTH){
    		
    		int monthsLeft = (int) (timeLeftInMillis / MILLISECONDS_IN_MONTH);
    		if(monthsLeft>1){
    			return monthsLeft + " months left";
    		}else{
    			return monthsLeft + " month left";
    		}
    	
    	}else if(timeLeftInMillis > MILLISECONDS_IN_WEEK){
    		
    		int weeksLeft = (int) (timeLeftInMillis / MILLISECONDS_IN_WEEK);
    		if(weeksLeft>1){
    			return weeksLeft+ " weeks left";
    		}else{
    			return weeksLeft + " week left";
    		}
    		
    	}else if(timeLeftInMillis > MILLISECONDS_IN_DAY){
    		
    		int daysLeft = (int) (timeLeftInMillis / MILLISECONDS_IN_DAY);
    		if(daysLeft>1){
    			return daysLeft+ " days left";
    		}else{
    			return daysLeft + " day left";
    		}
    		
    	}else if(timeLeftInMillis > MILLISECONDS_IN_HOUR){
    		
    		int hoursLeft = (int) (timeLeftInMillis / MILLISECONDS_IN_HOUR);
    		if(hoursLeft>1){
    			return hoursLeft + " hours left";
    		}else{
    			return hoursLeft + " hour left";
    		}
    	
    	}else if(timeLeftInMillis > MILLISECONDS_IN_MINUTE){
    		
    		int minsLeft = (int) (timeLeftInMillis / MILLISECONDS_IN_MINUTE);
    		if(minsLeft>1){
    			return minsLeft + " minutes left";
    		}else{
    			return minsLeft + " min left";
    		}
    	
    	}else if(timeLeftInMillis < MILLISECONDS_IN_MINUTE  && timeLeftInMillis > 0){
    		
    		int secLeft = (int) (timeLeftInMillis / 1000);
    		if(secLeft>1){
    			return secLeft + " seconds left";
    		}else{
    			return secLeft + " second left";
    		}
    	
    	}else{
    		return "Time Over";
    	}
    	
    }

    /**
     * Set the color according to time left
     *
     * @param date the scheduled date
     */
	public void setLabelColor(Date schDate){
		Calendar scheduledCal = GregorianCalendar.getInstance();
    	scheduledCal.setTime(schDate);
        
    	long timeLeftInMillis = scheduledCal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
    	if(timeLeftInMillis < 12*MILLISECONDS_IN_HOUR){
    		setColor(LESS_THAN_TWELVE_HOURS_COLOR);
    	}else if(timeLeftInMillis < 2*MILLISECONDS_IN_DAY){
    		setColor(LESS_THAN_TWO_DAYS_COLOR);
    	}else if(timeLeftInMillis < MILLISECONDS_IN_MONTH){
    		setColor(LESS_THAN_MONTH_COLOR);
    	}else{
    		setColor(MORE_THAN_MONTH_COLOR);
    	}
	}
	
    
	
}

