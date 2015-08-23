package com.resoneuronance.campus.web.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.resoneuronance.campus.web.dao.CollegeDAO;
import com.resoneuronance.campus.web.dao.TeacherDAO;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Notification;
import com.resoneuronance.campus.web.domain.StudentRegID;
import com.resoneuronance.campus.web.domain.StudentToTeacherMapping;
import com.resoneuronance.campus.web.domain.Teacher;
import com.resoneuronance.campus.web.domain.TeacherToDepartmentMapping;
import com.resoneuronance.campus.web.util.FileUtils;
import com.resoneuronance.campus.web.util.NotificationUtil;

public class TeacherBOImpl implements TeacherBO {

	private TeacherDAO teacherDao;
	private CollegeDAO collegeDao;
	private com.resoneuronance.campus.web.bo.domain.Teacher currentTeacher;
	
	public com.resoneuronance.campus.web.bo.domain.Teacher getCurrentTeacher() {
		return currentTeacher;
	}
	
	
	public TeacherDAO getTeacherDao() {
		return teacherDao;
	}
	
	public void setTeacherDao(TeacherDAO teacherDao) {
		this.teacherDao = teacherDao;
	} 
	
	public CollegeDAO getCollegeDao() {
		return collegeDao;
	}

	public void setCollegeDao(CollegeDAO collegeDao) {
		this.collegeDao = collegeDao;
	}

	@Override
	public boolean login(Teacher teacher, String collegeName) {
		Teacher teacherData = teacherDao.getTeacher(teacher.getEmail());
		if(teacherData==null || StringUtils.isBlank(teacher.getPassword()) || !StringUtils.equals(teacher.getPassword(), teacherData.getPassword())) {
			return false;
		}
		College college = teacherDao.getCollege(teacherData.getCollegeId());
		if(StringUtils.equals(college.getName(), collegeName)) {
			prepareTeacher(college,teacherData);
			return true;
		}
		teacherData = null;
		return false;
	}

	private void prepareTeacher(College college, Teacher teacherData) {
		currentTeacher = new com.resoneuronance.campus.web.bo.domain.Teacher();
		currentTeacher.setId(teacherData.getId());
		currentTeacher.setName(teacherData.getName());
		currentTeacher.setEmail(teacherData.getEmail());
		currentTeacher.setCollege(college);
		currentTeacher.setCurrentNotification(new Notification());
		setTaggedDepartments();
		prepareUntaggedDepartments();
		setNotifications();
	}

	private void setTaggedDepartments() {
		List<TeacherToDepartmentMapping> mappings = teacherDao.getDepartments(currentTeacher.getId());
		if(CollectionUtils.isEmpty(mappings)) {
			return;
		}
		currentTeacher.setTaggedDepartmentNames(new ArrayList<String>());
		currentTeacher.setTaggedDepartments(new ArrayList<Department>());
		for(TeacherToDepartmentMapping mapping:mappings) {
			Department department = collegeDao.getDepartment(mapping.getDepartmentId());
			currentTeacher.getTaggedDepartments().add(department);
			currentTeacher.getTaggedDepartmentNames().add(department.getName());
		}
	}

	@Override
	public boolean registerTeacher(Teacher teacher,String collegeName) {
		Teacher currentTeacher = teacherDao.getTeacher(teacher.getEmail());
		if(currentTeacher==null) {
			return false;
		}
		College college = teacherDao.getCollege(currentTeacher.getCollegeId());
		if(StringUtils.equals(college.getName(), collegeName)) {
			teacherDao.updateTeacher(teacher);
			return true;
		}
		return false;
	}

	public void prepareUntaggedDepartments() {
		if(currentTeacher.getCollege() == null) {
			return;
		}
		List<Department> allDepartments =  collegeDao.getAllDepartments(currentTeacher.getCollege().getId());
		if(CollectionUtils.isEmpty(allDepartments)) {
			return;
		}
		if(CollectionUtils.isEmpty(currentTeacher.getTaggedDepartments())) {
			currentTeacher.setUntaggedDepartments(allDepartments);
			currentTeacher.setUntaggedDepartmentsNames();
			return;
		}
		currentTeacher.setUntaggedDepartments(new ArrayList<Department>());
		currentTeacher.setUntaggedDepartmentNames(new ArrayList<String>());
		for(Department department:allDepartments) {
			boolean isTagged = false;
			for(Department taggedDepartment:currentTeacher.getTaggedDepartments()) {
				if(taggedDepartment.getId() == department.getId()) {
					isTagged = true;
					break;
				}
			}
			if(!isTagged) {
				currentTeacher.getUntaggedDepartments().add(department);
				currentTeacher.getUntaggedDepartmentNames().add(department.getName());
			}
		}
	}


	@Override
	public void tagDepartmentToTeacher(com.resoneuronance.campus.web.bo.domain.Teacher teacher) {
		TeacherToDepartmentMapping mapping = new TeacherToDepartmentMapping();
		for(Department department:currentTeacher.getUntaggedDepartments()) {
			if(StringUtils.equals(department.getName(), teacher.getDepartmentToTag())) {
				mapping.setTeacherId(currentTeacher.getId());
				mapping.setDepartmentId(department.getId());
				teacherDao.addDepartmentMapping(mapping);
				break;
			}
		}
		setTaggedDepartments();
		prepareUntaggedDepartments();
	}


	@Override
	public void notify(Notification notification,MultipartFile file) throws IOException {
		notification.setTeacherId(currentTeacher.getId());
		notification.setDate(new Date());
		currentTeacher.setCurrentNotification(notification);
		FileUtils.uploadTeacherDocument(file, currentTeacher);
		teacherDao.addNotification(currentTeacher.getCurrentNotification());
		currentTeacher.setCurrentNotification(new Notification());
		List<StudentToTeacherMapping> mappedStudents = teacherDao.getMappedStudents(currentTeacher.getId());
		setNotifications();
		NotificationUtil.notifyStudents(prepareStudentRegIdMappings(mappedStudents),new Gson().toJson(notification));
	}


	private List<StudentRegID> prepareStudentRegIdMappings(List<StudentToTeacherMapping> mappedStudents) {
		List<StudentRegID> studentRegIds = new ArrayList<StudentRegID>();
		for(StudentToTeacherMapping mapping:mappedStudents) {
			List<StudentRegID> regIds = teacherDao.getStudentRegId(mapping.getStudentId());
			studentRegIds.addAll(regIds);
		}
		return studentRegIds;
	}


	private void setNotifications() {
		List<Notification> allNotifications = teacherDao.getNotifications(currentTeacher.getId());
		currentTeacher.setCoCurricularNotifications(new ArrayList<Notification>());
		currentTeacher.setCurricularNotifications(new ArrayList<Notification>());
		currentTeacher.setDepartmentNotifications(new ArrayList<Notification>());
		for(Notification currentNotification:allNotifications) {
			if(StringUtils.equals("Co-Curricular", currentNotification.getFormat())) {
				currentTeacher.getCoCurricularNotifications().add(currentNotification);
			}
			else if(StringUtils.equals("Curricular", currentNotification.getFormat())) {
				currentTeacher.getCurricularNotifications().add(currentNotification);
			}
			else {
				checkForDepartmentNotification(currentNotification);
			}
			
		}
	}



	private void checkForDepartmentNotification(Notification currentNotification) {
		if(currentTeacher.getCurrentDepartment() == null) {
			return;
		}
		
		if(currentNotification.getDepartmentId() == currentTeacher.getCurrentDepartment().getId() && StringUtils.equals("Department", currentNotification.getType())) {
			currentTeacher.getDepartmentNotifications().add(currentNotification);
		}		
	}


	@Override
	public void deleteNotification(int id) {
		teacherDao.deleteNotification(id);
	}


	@Override
	public InputStream downloadDocument(int id) throws FileNotFoundException {
		Notification notification = teacherDao.getNotification(id);
		if(notification != null) {
			InputStream is = new FileInputStream(new File(notification.getFilePath()));
			return is;
		}
		return null;
	}


	@Override
	public void loadDepartment(int id) {
		Department department = teacherDao.getDepartment(id);
		currentTeacher.setCurrentDepartment(department);
		setNotifications();
	}

}
