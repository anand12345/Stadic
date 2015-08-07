package com.resoneuronance.campus.web.bo.domain;

import java.util.List;

import com.resoneuronance.campus.web.domain.Notification;

public class StudentTeacher {
	
	private int id;
	private String name;
	private String email;
	private List<Notification> notifications;
	private int latestCount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLatestCount() {
		return latestCount;
	}
	public void setLatestCount(int latestCount) {
		this.latestCount = latestCount;
	}
	

}
