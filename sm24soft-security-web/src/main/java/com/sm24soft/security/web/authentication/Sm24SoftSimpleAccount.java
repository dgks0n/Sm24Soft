package com.sm24soft.security.web.authentication;

public class Sm24SoftSimpleAccount {
	
	private String username;
	private String password;
	
	public Sm24SoftSimpleAccount(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}
