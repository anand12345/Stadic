package com.resoneuronance.campus.web.bo;

import java.util.List;

import com.resoneuronance.campus.web.bo.domain.StudentDepartment;
import com.resoneuronance.campus.web.bo.domain.StudentTeacher;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;

public interface StudentBO {

	boolean login(Student student, String collegeName);
	boolean registerStudent(Student teacher, String collegeName, String departmentName);
	com.resoneuronance.campus.web.bo.domain.Student getCurrentStudent();
	List<Teacher> getCurrentTeachers();
	void tagTeachers(int[] teacherIds);
	StudentTeacher getNotifications(int teacherId);
	void updateTeacherNotifications(StudentTeacher studentTeacher);
	void updateStudentToTeacherMapping(StudentTeacher studentTeacher);
	void prepareStudentTeachers();
	void updateDepartmentNotifications(StudentDepartment studentDepartment);
	List<Department> getCurrentDepartments();
	void tagDepartments(int[] departmentIds);
	void prepareStudentDepartments();
	void updateStudentToDepartmentMapping(StudentDepartment studentDepartment);
	void prepareStudent(College college, Student student);
	void prepareCurrentStudent(com.resoneuronance.campus.web.bo.domain.Student student);
	void storeRegId(String studentRegIdJson);
}
