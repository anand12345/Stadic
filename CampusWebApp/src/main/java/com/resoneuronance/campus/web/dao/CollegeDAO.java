package com.resoneuronance.campus.web.dao;

import java.util.List;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;

public interface CollegeDAO {
	
	public List<College> getAllColleges();
	void save(College college);
	public College getCollege(String collegeName);
	public void addDepartment(Department department);
	void addDepartments(List<Department> department);
	public List<Department> getAllDepartments(int collegeId);
	public void deleteDepartment(int departmentId);

}
