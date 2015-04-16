package com.resoneuronance.campus.web.bo;

import java.util.List;

import com.resoneuronance.campus.web.domain.College;

public interface CollegeBO {
	
	public List<College> getColleges();
	public void addNew(College college);

}
