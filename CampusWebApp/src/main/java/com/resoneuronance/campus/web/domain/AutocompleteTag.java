package com.resoneuronance.campus.web.domain;

public class AutocompleteTag {
	
	private int id;
	private String tagName;
 
	//getter and setter methods
 
	public AutocompleteTag(int id, String tagName) {
		this.setId(id);
		this.setTagName(tagName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	

}
