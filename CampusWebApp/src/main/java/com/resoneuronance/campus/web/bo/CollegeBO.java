package com.resoneuronance.campus.web.bo;

import java.util.List;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;

public interface CollegeBO {
	
	public List<College> getColleges();
	public void addNew(College college);
	public List<String> getCollegeNames();
	public boolean login(College college);
	public College getCurrentCollege();
	public void addDepartments(List<Department> departments);
	public List<Department> getCurrentDepartments();
	public void deleteDepartment(String id);
}
