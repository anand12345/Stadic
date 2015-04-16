package com.resoneuronance.campus.web.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.resoneuronance.campus.web.dao.CollegeDAO;
import com.resoneuronance.campus.web.domain.College;

public class CollegeBOImpl implements CollegeBO {
	
	private CollegeDAO collegeDao;

	@Override
	@Transactional
	public List<College> getColleges() {
		return collegeDao.getAllColleges();
	}

	public CollegeDAO getCollegeDao() {
		return collegeDao;
	}

	public void setCollegeDao(CollegeDAO collegeDao) {
		this.collegeDao = collegeDao;
	}

	@Override
	public void addNew(College college) {
		collegeDao.save(college);
	}

}
