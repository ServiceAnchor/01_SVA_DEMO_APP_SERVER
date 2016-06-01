package com.sva.model;

public class SubsribeModel {
	private long timestamp;
	
	private long time_begin;
	
	private long last_sub_time;
	
	private int sub_type;
	
	private int sub_count;
	
	private int request_count;
	
	private String userID;
	
	private int storeID;
	
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getTime_begin() {
		return time_begin;
	}
	public void setTime_begin(long time_begin) {
		this.time_begin = time_begin;
	}
	public long getLast_sub_time() {
		return last_sub_time;
	}
	public void setLast_sub_time(long last_sub_time) {
		this.last_sub_time = last_sub_time;
	}
	public int getSub_count() {
		return sub_count;
	}
	public void setSub_count(int sub_count) {
		this.sub_count = sub_count;
	}
	public int getRequest_count() {
		return request_count;
	}
	public void setRequest_count(int request_count) {
		this.request_count = request_count;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getSub_type() {
		return sub_type;
	}
	public void setSub_type(int sub_type) {
		this.sub_type = sub_type;
	}
	public int getStoreID() {
		return storeID;
	}
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	
	

}
