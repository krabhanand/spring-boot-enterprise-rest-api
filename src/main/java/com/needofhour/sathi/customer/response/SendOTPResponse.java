package com.needofhour.sathi.customer.response;

public class SendOTPResponse {
	public SendOTPResponse(){
		
	}

	private String valueType,value;

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
