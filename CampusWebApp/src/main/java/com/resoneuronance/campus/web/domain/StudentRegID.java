package com.resoneuronance.campus.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stud_regid")
public class StudentRegID {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "student_id")
	private int studentId;
	
	@Column(name = "reg_id")
	private String regId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	public String getRegId() {
		return regId;
	}
	
	
}
