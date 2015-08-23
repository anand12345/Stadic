package com.resoneuronance.campus.web.dao;

import java.util.List;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Notification;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.StudentRegID;
import com.resoneuronance.campus.web.domain.StudentToDepartmentMapping;
import com.resoneuronance.campus.web.domain.StudentToTeacherMapping;
import com.resoneuronance.campus.web.domain.Teacher;

public interface StudentDAO {
	
	Student getStudent(String studentEmail);
	void updateStudent(com.resoneuronance.campus.web.domain.Student student);
	College getCollege(int collegeId);
	Department getDepartment(String departmentName, int id);
	Department getDepartment(int departmentId);
	List<Notification> getDepartmentNotifications(int id, String year);
	List<Teacher> getAllTeachers(int id);
	void addTeacherMapping(StudentToTeacherMapping mapping);
	List<StudentToTeacherMapping> getTeacherMappings(int id);
	List<Notification> getTeacherNotifications(int id, List<Integer> list, String year);
	void updateTeacherMapping(StudentToTeacherMapping mapping);
	List<Department> getAllDepartments(int id);
	List<StudentToDepartmentMapping> getDepartmentMappings(int id);
	void addDepartmentMapping(StudentToDepartmentMapping mapping);
	void updateDepartmentMapping(StudentToDepartmentMapping mapping);
	void addRegId(StudentRegID studentRegID);
	List<StudentRegID> getRegIds(int studentId);
}
