package com.sms.scheduler.database;

public class Contact {
	String number;
	String name;
	public Contact(){
		this.number="";
		this.name="";
	}
	public Contact(String name, String number){
		this.number=number;
		this.name=name;		
	}
	public String getNumber(){
		return number;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public void setNumber(String number){
		this.number = number;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
