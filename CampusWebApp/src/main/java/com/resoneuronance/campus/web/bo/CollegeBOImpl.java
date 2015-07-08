package com.resoneuronance.campus.web.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.resoneuronance.campus.web.dao.CollegeDAO;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;

public class CollegeBOImpl implements CollegeBO {

	private static final String COLLEGE_SELECT_0 = "Select a college";
	private CollegeDAO collegeDao;
	private College currentCollege;
	private List<Department> currentDepartments;
	private List<Teacher> currentTeachers;
	private List<Student> currentStudents;

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
			updateTeachers();
			return true;
		}
		return false;
	}

	@Override
	public void addDepartments(List<Department> departments) {
		for(Department dept:departments) {
			dept.setCollegeId(currentCollege.getId());
		}
		collegeDao.addDepartments(departments);
		updateDepartments();
	}

	public List<Department> getCurrentDepartments() {
		updateDepartments();
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

	@Override
	public void addDepartment(Department department) {
		department.setCollegeId(currentCollege.getId());
		collegeDao.addDepartment(department);
		updateDepartments();
	}

	@Override
	public void addTeachers(List<Teacher> teachers) {
		for(Teacher teacher:teachers) {
			teacher.setCollegeId(currentCollege.getId());
		}
		collegeDao.addTeachers(teachers);
		updateTeachers();
	}

	private void updateTeachers() {
		currentTeachers = collegeDao.getAllTeachers(currentCollege.getId());
	}

	@Override
	public List<Teacher> getCurrentTeachers() {
		updateTeachers();
		return currentTeachers;
	}

	@Override
	public void deleteTeacher(String id) {
		collegeDao.deleteTeacher(Integer.parseInt(id));
		updateTeachers();		
	}

	@Override
	public void addTeacher(Teacher teacher) {
		teacher.setCollegeId(currentCollege.getId());
		collegeDao.addTeacher(teacher);
		updateTeachers();
		
	}

	@Override
	public void addStudents(List<Student> students) {
		for(Student student:students) {
			student.setCollegeId(currentCollege.getId());
			student.setDepartmentId(0);
		}
		collegeDao.addStudents(students);
		updateStudents();		
	}

	private void updateStudents() {
		currentStudents = collegeDao.getAllStudents(currentCollege.getId());
	}

	@Override
	public List<Student> getCurrentStudents() {
		updateStudents();
		return currentStudents;
	}

	@Override
	public void deleteStudent(String id) {
		collegeDao.deleteStudent(Integer.parseInt(id));
		updateStudents();
	}

	@Override
	public void addStudent(Student student) {
		student.setCollegeId(currentCollege.getId());
		student.setDepartmentId(0);
		collegeDao.addStudent(student);
		updateStudents();		
	}

	@Override
	public Student getStudent(int studentId) {
		return collegeDao.getStudent(studentId);
	}

	@Override
	public void editStudent(Student student) {
		collegeDao.updateStudent(student);
	}

	@Override
	public Department getDepartment(int id) {
		return collegeDao.getDepartment(id);
	}

	@Override
	public void editDepartment(Department department) {
		collegeDao.updateDepartment(department);
	}

	@Override
	public Teacher getTeacher(int id) {
		return collegeDao.getTeacher(id);
	}

	@Override
	public void editTeacher(Teacher teacher) {
		collegeDao.updateTeacher(teacher);
	}

}
