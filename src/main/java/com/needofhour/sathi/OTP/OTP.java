package com.needofhour.sathi.OTP;

import java.util.Random;

public class OTP {
	public OTP(){
		
	}
	
	public String getOTP(){
		Random r=new Random();
		Integer l=r.nextInt(90000000)+10000000;
		String OTP=l.toString();
		return OTP;
		}

}
