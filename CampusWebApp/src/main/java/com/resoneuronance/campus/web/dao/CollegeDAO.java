package com.resoneuronance.campus.web.dao;

import java.util.List;

import com.resoneuronance.campus.web.domain.College;

public interface CollegeDAO {
	
	public List<College> getAllColleges();
	void save(College college);

}
