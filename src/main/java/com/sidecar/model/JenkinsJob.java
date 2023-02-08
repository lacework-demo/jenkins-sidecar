package com.sidecar.model;
import java.util.Date;

public class JenkinsJob {

	private int id;
	private String job;
	private Date insertTime;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String name) {
		this.job = name;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}
