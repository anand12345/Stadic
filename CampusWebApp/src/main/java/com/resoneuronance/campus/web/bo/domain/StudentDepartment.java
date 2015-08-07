package com.resoneuronance.campus.web.bo.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.resoneuronance.campus.web.domain.Notification;

public class StudentDepartment {
	
	private int id;
	private String name;
	private List<Notification> notifications;
	private int latestCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Notification> getNotifications() {
		if(CollectionUtils.isEmpty(notifications)) {
			notifications= new ArrayList<Notification>();
		}
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
