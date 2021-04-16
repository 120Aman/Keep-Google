package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "db_sequence")
public class DbSequence {
    @Id
    private String  id;
    private Long seq;
	public DbSequence() {
	}
	public DbSequence(String id, Long seq) {
		super();
		this.id = id;
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "DbSequence [id=" + id + ", seq=" + seq + "]";
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	
}
