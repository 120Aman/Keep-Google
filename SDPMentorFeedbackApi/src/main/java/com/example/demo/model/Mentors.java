package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Mentors")
public class Mentors {

	@Id
	private Long id;
	private String fullName;
	private String username;
	private String password;
	private boolean loggedIn;
	
	public Mentors() {
		super();
	}
	public Mentors(Long id, String fullName, String username, String password, boolean loggedIn) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.loggedIn = loggedIn;
	}
	@Override
	public String toString() {
		return "Mentors [id=" + id + ", fullName=" + fullName + ", username=" + username + ", password=" + password
				+ ", loggedIn=" + loggedIn + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	
}
