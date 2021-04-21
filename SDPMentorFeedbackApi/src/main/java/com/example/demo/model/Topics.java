package com.example.demo.model;

public class Topics {
	private Long topicId;
	private String topicName;
	private boolean doneStatus;
	
	public Topics() {
		super();
	}
	public Topics(Long topicId, String topicName, boolean doneStatus) {
		super();
		this.topicId = topicId;
		this.topicName = topicName;
		this.doneStatus = doneStatus;
	}
	@Override
	public String toString() {
		return "Topics [topicId=" + topicId + ", topicName=" + topicName + ", doneStatus=" + doneStatus + "]";
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public boolean isDoneStatus() {
		return doneStatus;
	}
	public void setDoneStatus(boolean doneStatus) {
		this.doneStatus = doneStatus;
	}

	
}
