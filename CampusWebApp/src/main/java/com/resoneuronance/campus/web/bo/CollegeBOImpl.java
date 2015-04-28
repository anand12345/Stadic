package com.resoneuronance.campus.web.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.resoneuronance.campus.web.dao.CollegeDAO;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;

public class CollegeBOImpl implements CollegeBO {

	private static final String COLLEGE_SELECT_0 = "Select a college";
	private CollegeDAO collegeDao;
	private College currentCollege;
	private List<Department> currentDepartments;

	public College getCurrentCollege() {
		return currentCollege;
	}

	public void setCurrentCollege(College currentCollege) {
		this.currentCollege = currentCollege;
	}

	@Override
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

	@Override
	public List<String> getCollegeNames() {
		List<College> colleges = getColleges();
		List<String> collegeNames = new ArrayList<String>();
		collegeNames.add(COLLEGE_SELECT_0);
		if (CollectionUtils.isNotEmpty(colleges)) {
			for (College colllege : colleges) {
				collegeNames.add(colllege.getName());
			}
		}
		return collegeNames;
	}

	@Override
	public boolean login(College college) {
		currentCollege = collegeDao.getCollege(college.getName());
		if(currentCollege == null) {
			return false;
		}
		if (StringUtils.equals(college.getUsername(), currentCollege.getUsername())
				&& StringUtils.equals(college.getPassword(), currentCollege.getPassword())) {
			System.out.println("Id B4:" + college.getId());
			updateDepartments();
			return true;
		}
		return false;
	}

	@Override
	public void addDepartments(List<Department> departments) {
		collegeDao.addDepartments(departments);
		updateDepartments();
	}

	public List<Department> getCurrentDepartments() {
		return currentDepartments;
	}

	public void setCurrentDepartments(List<Department> currentDepartments) {
		this.currentDepartments = currentDepartments;
	}

	@Override
	public void deleteDepartment(String id) {
		collegeDao.deleteDepartment(Integer.parseInt(id));
		updateDepartments();
	}

	private void updateDepartments() {
		currentDepartments = collegeDao.getAllDepartments(currentCollege.getId());
	}
	

}
