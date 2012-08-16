package com.touchlab.main;

public class User {
	
	String username;
	String description;
	String url;
	
	public User(){
		username = "default";
		description = "default";
		url = "";
	}
	
	public User(String username, String description, String url) {
		super();
		this.username = username;
		this.description = description;
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
