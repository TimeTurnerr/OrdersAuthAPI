package com.shoppingapp.authenticated.model;

public class JwtRequest {

	String username;
	String password;
	String userType;

	public JwtRequest() {
	}

	public JwtRequest(String username, String password, String userType) {
		this.username = username;
		this.password = password;
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "JwtRequest [username=" + username + ", password=" + password + ", userType=" + userType + "]";
	}

}
