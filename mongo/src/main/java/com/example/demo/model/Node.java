package com.example.demo.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Nodes")
public class Node {
	
	 @Transient
	    public static final String SEQUENCE_NAME = "node_sequence";
@Id
private Long id;
private String Title;
private String Note;
private boolean DeleteStatus;
private boolean Pinned;

public Node() {
}

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
@Override
public String toString() {
	return "Node [id=" + id + ", Title=" + Title + ", Note=" + Note + ", DeleteStatus=" + DeleteStatus + ", Pinned="
			+ Pinned + "]";
}

public Node(Long id, String title, String note, boolean deleteStatus, boolean pinned) {
	super();
	this.id = id;
	Title = title;
	Note = note;
	DeleteStatus = deleteStatus;
	Pinned = pinned;
}

public Node(String title, String note, boolean deleteStatus,boolean pinned) {
	super();
	Title = title;
	Note = note;
	DeleteStatus = deleteStatus;
	Pinned = pinned;
}
public Node(String title, String note) {
	super();
	Title = title;
	Note = note;
}


public String getTitle() {
	return Title;
}
public void setTitle(String title) {
	Title = title;
}
public String getNote() {
	return Note;
}
public void setNote(String note) {
	Note = note;
}
public boolean isDeleteStatus() {
	return DeleteStatus;
}
public void setDeleteStatus(boolean deleteStatus) {
	DeleteStatus = deleteStatus;
}
public boolean isPinned() {
	return Pinned;
}
public void setPinned(boolean pinned) {
	Pinned = pinned;
}
}
