package com.resoneuronance.campus.web.bo.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Notification;

public class Teacher {
	
	private String name;
	private int id;
	private String email;
	private List<Department> taggedDepartments;
	private List<Department> untaggedDepartments;
	private List<String> taggedDepartmentNames;
	private List<String> untaggedDepartmentNames;
	private String departmentToTag;
	private Notification currentNotification;
	private Department currentDepartment;
	private College college;
	private List<Notification> coCurricularNotifications;
	private List<Notification> curricularNotifications;
	private List<Notification> departmentNotifications;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Department> getTaggedDepartments() {
		if(CollectionUtils.isEmpty(taggedDepartments)) {
			taggedDepartments = new ArrayList<Department>();
		}
		return taggedDepartments;
	}
	public void setTaggedDepartments(List<Department> taggedDepartments) {
		this.taggedDepartments = taggedDepartments;
	}
	public List<Department> getUntaggedDepartments() {
		if(CollectionUtils.isEmpty(untaggedDepartments)) {
			untaggedDepartments = new ArrayList<Department>();
		}
		return untaggedDepartments;
	}
	public void setUntaggedDepartments(List<Department> untaggedDepartments) {
		this.untaggedDepartments = untaggedDepartments;
	}
	public List<String> getTaggedDepartmentNames() {
		if(CollectionUtils.isEmpty(taggedDepartmentNames)) {
			taggedDepartmentNames = new ArrayList<String>();
		}
		return taggedDepartmentNames;
	}
	public void setTaggedDepartmentNames(List<String> taggedDepartmentNames) {
		this.taggedDepartmentNames = taggedDepartmentNames;
	}
	public List<String> getUntaggedDepartmentNames() {
		if(CollectionUtils.isEmpty(untaggedDepartmentNames)) {
			untaggedDepartmentNames = new ArrayList<String>();
		}
		return untaggedDepartmentNames;
	}
	public void setUntaggedDepartmentNames(List<String> untaggedDepartmenNames) {
		this.untaggedDepartmentNames = untaggedDepartmenNames;
	}

	public void setUntaggedDepartmentsNames() {
		untaggedDepartmentNames = convertToString(untaggedDepartments);
	}

	private List<String> convertToString(List<Department> untaggedDepartments) {
		List<String> departments = new ArrayList<String>();
		for(Department department:untaggedDepartments) {
			departments.add(department.getName());
		}
		return departments;
	}
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	public String getDepartmentToTag() {
		return departmentToTag;
	}
	public void setDepartmentToTag(String departmentToTag) {
		this.departmentToTag = departmentToTag;
	}
	public Notification getCurrentNotification() {
		return currentNotification;
	}
	public void setCurrentNotification(Notification currentNotification) {
		this.currentNotification = currentNotification;
	}
	public List<Notification> getCoCurricularNotifications() {
		if(CollectionUtils.isEmpty(coCurricularNotifications)) {
			coCurricularNotifications = new ArrayList<Notification>();
		}
		return coCurricularNotifications;
	}
	public void setCoCurricularNotifications(List<Notification> coCurricularNotifications) {
		this.coCurricularNotifications = coCurricularNotifications;
	}
	public List<Notification> getCurricularNotifications() {
		if(CollectionUtils.isEmpty(curricularNotifications)) {
			curricularNotifications = new ArrayList<Notification>();
		}
		return curricularNotifications;
	}
	public void setCurricularNotifications(List<Notification> curricularNotifications) {
		this.curricularNotifications = curricularNotifications;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Department getCurrentDepartment() {
		return currentDepartment;
	}
	public void setCurrentDepartment(Department currentDepartment) {
		this.currentDepartment = currentDepartment;
	}
	public List<Notification> getDepartmentNotifications() {
		if(CollectionUtils.isEmpty(departmentNotifications)) {
			departmentNotifications = new ArrayList<Notification>();
		}
		return departmentNotifications;
	}
	public void setDepartmentNotifications(List<Notification> departmentNotifications) {
		this.departmentNotifications = departmentNotifications;
	}
}
