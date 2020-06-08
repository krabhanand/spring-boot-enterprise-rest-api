package com.needofhour.sathi.customer.response;

public class VerifyRegIdResponse {
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

	public VerifyRegIdResponse(String valueType, String value) {
		super();
		this.valueType = valueType;
		this.value = value;
	}

	public VerifyRegIdResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
