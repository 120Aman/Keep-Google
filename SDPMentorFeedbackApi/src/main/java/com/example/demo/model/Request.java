package com.example.demo.model;

public class Request {
	private Long id;
	private String date;

	public Request() {
		super();
	}

	public Request(Long id, String date) {
		super();
		this.id = id;
		this.date = date;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", date=" + date + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
