package com.resoneuronance.campus.web.bo;

import java.util.List;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;

public interface CollegeBO {
	//College business Interface
	
	public List<College> getColleges();
	public void addNew(College college);
	public List<String> getCollegeNames();
	public boolean login(College college);
	public College getCurrentCollege();
	
	public void addDepartments(List<Department> departments);
	public List<Department> getCurrentDepartments();
	public void deleteDepartment(String id);
	public void addDepartment(Department department);
	
	public void addTeachers(List<Teacher> teachers);
	public List<Teacher> getCurrentTeachers();
	public void deleteTeacher(String id);
	public void addTeacher(Teacher teacher);
	
	public void addStudents(List<Student> students);
	public List<Student> getCurrentStudents();
	public void deleteStudent(String id);
	public void addStudent(Student student);
	
	
}
