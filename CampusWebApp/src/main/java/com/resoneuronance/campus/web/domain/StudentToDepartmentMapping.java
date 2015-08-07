package com.resoneuronance.campus.web.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "stud_dept")
public class StudentToDepartmentMapping {

	@Id
	@GeneratedValue
	@Column(name= "stud_dept_id")
	private int id;
	
	@Column(name = "dept_id")
	private int departmentId;
	
	@Column(name = "stud_id")
	private int studentId;
	
	@Column(name = "last_seen")
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	private Date lastSeen;

	public Date getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Date lastSeen) {
		this.lastSeen = lastSeen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDepartmentId() {
		return departmentId;
	}
	
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
}
