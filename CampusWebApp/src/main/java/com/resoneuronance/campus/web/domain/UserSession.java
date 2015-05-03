package com.resoneuronance.campus.web.domain;

public class UserSession {
	
	private Object sessionObject;
	private UserType type;
	
	public Object getSessionObject() {
		return sessionObject;
	}
	public void setSessionObject(Object sessionObject) {
		this.sessionObject = sessionObject;
	}
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}

}
