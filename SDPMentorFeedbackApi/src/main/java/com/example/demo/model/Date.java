package com.example.demo.model;

import java.util.List;

public class Date {

	private String date;
	private boolean candidateStatus;
	private List<Topics> topics;

	public Date() {
		super();
	}

	public Date(String date, boolean candidateStatus, List<Topics> topics) {
		super();
		this.date = date;
		this.candidateStatus = candidateStatus;
		this.topics = topics;
	}

	@Override
	public String toString() {
		return "Date [date=" + date + ", candidateStatus=" + candidateStatus + ", topics=" + topics + "]";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(boolean candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	public List<Topics> getTopics() {
		return topics;
	}

	public void setTopics(List<Topics> topics) {
		this.topics = topics;
	}
}
