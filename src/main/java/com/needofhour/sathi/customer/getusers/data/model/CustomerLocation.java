package com.needofhour.sathi.customer.getusers.data.model;

public class CustomerLocation {
	private String name;
	private String phone;
	private String distance;
	
	
	
	public CustomerLocation(String name, String phone, String distance) {
		super();
		this.name = name;
		this.phone = phone;
		this.distance = distance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	
	

}
