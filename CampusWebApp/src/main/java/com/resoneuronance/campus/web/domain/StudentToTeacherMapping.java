package com.resoneuronance.campus.web.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "stud_teacher")
public class StudentToTeacherMapping {

	@Id
	@GeneratedValue
	@Column(name= "id")
	private int id;
	
	@Column(name = "teacher_id")
	private int teacherId;
	
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

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
}
