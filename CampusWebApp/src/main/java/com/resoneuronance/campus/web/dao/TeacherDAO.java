package com.resoneuronance.campus.web.dao;

import java.util.List;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Notification;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.StudentRegID;
import com.resoneuronance.campus.web.domain.StudentToTeacherMapping;
import com.resoneuronance.campus.web.domain.Teacher;
import com.resoneuronance.campus.web.domain.TeacherToDepartmentMapping;

public interface TeacherDAO {

	Teacher getTeacher(String teacherEmail);
	College getCollege(int collegeId);
	void updateTeacher(Teacher teacher);
	void addDepartmentMapping(TeacherToDepartmentMapping mapping);
	List<TeacherToDepartmentMapping> getDepartments(int teacherId);
	void addNotification(Notification notification);
	List<Notification> getNotifications(int teacherId);
	void deleteNotification(int id);
	Notification getNotification(int id);
	Department getDepartment(int id);
	List<StudentToTeacherMapping> getMappedStudents(int teacherId);
	List<StudentRegID> getStudentRegId(int studentId);
	Student getFilteredStudent(Notification notification,int studentId);
	
}
