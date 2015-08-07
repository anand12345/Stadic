package com.resoneuronance.campus.web.bo.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.resoneuronance.campus.web.domain.College;

public class Student {

	private int id;
	private String name;
	private String email;
	private String year;
	private College college;
	private int teacherNotificationsCount;
	private int departmentNotificationsCount;
	private StudentTeacher currentTeacher;
	private StudentDepartment currentDepartment;
	private List<StudentTeacher> teachers;
	private List<StudentDepartment> departments;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<StudentTeacher> getTeachers() {
		if(CollectionUtils.isEmpty(teachers)) {
			teachers = new ArrayList<StudentTeacher>();
		}
		return teachers;
	}
	public void setTeachers(List<StudentTeacher> teachers) {
		this.teachers = teachers;
	}
	public List<StudentDepartment> getDepartments() {
		if(CollectionUtils.isEmpty(departments)) {
			departments = new ArrayList<StudentDepartment>();
		}
		return departments;
	}
	public void setDepartments(List<StudentDepartment> departments) {
		this.departments = departments;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTeacherNotificationsCount() {
		return teacherNotificationsCount;
	}
	public void setTeacherNotificationsCount(int teacherNotificationsCount) {
		this.teacherNotificationsCount = teacherNotificationsCount;
	}
	public int getDepartmentNotificationsCount() {
		return departmentNotificationsCount;
	}
	public void setDepartmentNotificationsCount(int departmentNotificationsCount) {
		this.departmentNotificationsCount = departmentNotificationsCount;
	}
	public StudentTeacher getCurrentTeacher() {
		return currentTeacher;
	}
	public void setCurrentTeacher(StudentTeacher currentTeacher) {
		this.currentTeacher = currentTeacher;
	}
	public StudentDepartment getCurrentDepartment() {
		return currentDepartment;
	}
	public void setCurrentDepartment(StudentDepartment currentDepartment) {
		this.currentDepartment = currentDepartment;
	}
}
