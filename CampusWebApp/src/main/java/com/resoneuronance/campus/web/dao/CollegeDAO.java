package com.resoneuronance.campus.web.dao;

import java.util.List;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;

public interface CollegeDAO {
	
	public List<College> getAllColleges();
	void save(College college);
	public College getCollege(String collegeName);
	
	public void addDepartment(Department department);
	void addDepartments(List<Department> department);
	public List<Department> getAllDepartments(int collegeId);
	public void deleteDepartment(int departmentId);
	Department getDepartment(int studentId);
	void updateDepartment(Department department);
	
	public List<Teacher> getAllTeachers(int collegeId);
	public void addTeachers(List<Teacher> teachers);
	public void addTeacher(Teacher teacher);
	public void deleteTeacher(int teacherId);
	Teacher getTeacher(int teacherId);
	void updateTeacher(Teacher teacher);
	
	public List<Student> getAllStudents(int collegeId);
	public void addStudents(List<Student> students);
	public void addStudent(Student student);
	public void deleteStudent(int studentId);
	Student getStudent(int studentId);
	void updateStudent(Student student);
}
