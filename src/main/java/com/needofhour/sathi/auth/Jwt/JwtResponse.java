package com.needofhour.sathi.auth.Jwt;

public class JwtResponse {
	private String jwt;

	public JwtResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
	

}
