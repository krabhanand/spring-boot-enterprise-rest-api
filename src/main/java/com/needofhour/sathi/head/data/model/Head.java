package com.needofhour.sathi.head.data.model;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Head {
	
	private String unitName;
	@Id
	private String userName;
	private String password;
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
/*
 * 
 * 		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>.
		
		{
	"unitName" : "Insani Tigers",
	"userName" : "Insers",
	"password" : "pass1"
}
{
	"unitName" : "Insani Tigers 2",
	"userName" : "Insers2",
	"password" : "pass2"
}
{
	"unitName" : "Insani Tigers 3",
	"userName" : "Insers3",
	"password" : "pass3"
}
		
		
		
		
		
 * */
 
}
