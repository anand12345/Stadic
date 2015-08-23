package com.resoneuronance.campus.web.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.resoneuronance.campus.web.bo.domain.Student;
import com.resoneuronance.campus.web.bo.domain.StudentDepartment;
import com.resoneuronance.campus.web.bo.domain.StudentTeacher;
import com.resoneuronance.campus.web.dao.CollegeDAO;
import com.resoneuronance.campus.web.dao.StudentDAO;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Notification;
import com.resoneuronance.campus.web.domain.StudentRegID;
import com.resoneuronance.campus.web.domain.StudentToDepartmentMapping;
import com.resoneuronance.campus.web.domain.StudentToTeacherMapping;
import com.resoneuronance.campus.web.domain.Teacher;

public class StudentBOImpl implements StudentBO {

	private StudentDAO studentDao;
	private CollegeDAO collegeDao;
	
	private Student currentStudent;
	
	public StudentDAO getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(StudentDAO studentDao) {
		this.studentDao = studentDao;
	}

	public CollegeDAO getCollegeDao() {
		return collegeDao;
	}

	public void setCollegeDao(CollegeDAO collegeDao) {
		this.collegeDao = collegeDao;
	}
	
	@Override
	public Student getCurrentStudent() {
		return currentStudent;
	}

	public void setCurrentStudent(Student currentStudent) {
		this.currentStudent = currentStudent;
	}
	
	@Override
	public boolean login(com.resoneuronance.campus.web.domain.Student inputStudent, String collegeName) {
		com.resoneuronance.campus.web.domain.Student student = studentDao.getStudent(inputStudent.getEmail());
		if(student==null || StringUtils.isBlank(student.getPassword()) || !StringUtils.equals(student.getPassword(), inputStudent.getPassword())) {
			return false;
		}
		College college = studentDao.getCollege(student.getCollegeId());
		if(StringUtils.equals(college.getName(), collegeName)) {
			prepareStudent(college,student);
			return true;
		}
		return false;
	}
	
	@Override
	public void storeRegId(String regId) {
		StudentRegID studentRegID = new Gson().fromJson(regId, StudentRegID.class);
		if(studentRegID == null) {
			return;
		}
		List<StudentRegID> regIds = studentDao.getRegIds(studentRegID.getStudentId());
		for(StudentRegID reg:regIds) {
			if(StringUtils.equals(reg.getRegId(), studentRegID.getRegId())) {
				return;
			}
		}
		studentDao.addRegId(studentRegID);
		System.out.println("Reg ID received as .." + studentRegID.getRegId());
	}

	@Override
	public void prepareStudent(College college, com.resoneuronance.campus.web.domain.Student student) {
		currentStudent = new Student();
		currentStudent.setId(student.getId());
		currentStudent.setName(student.getName());
		currentStudent.setEmail(student.getEmail());
		currentStudent.setCollege(college);
		currentStudent.setYear(student.getYear());
		prepareStudentDepartments();
		prepareStudentTeachers();
	}

	@Override
	public void prepareStudentTeachers() {
		currentStudent.setTeacherNotificationsCount(0);
		currentStudent.setTeachers(new ArrayList<StudentTeacher>());
		List<StudentToTeacherMapping> mappings = studentDao.getTeacherMappings(currentStudent.getId());
		for(StudentToTeacherMapping mapping:mappings) {
			Teacher teacher = collegeDao.getTeacher(mapping.getTeacherId());
			prepareTeacherNotification(teacher,mapping.getLastSeen());
		}
	}

	private void prepareTeacherNotification(Teacher teacher, Date lastSeen) {
		StudentTeacher studentTeacher = new StudentTeacher();
		studentTeacher.setName(teacher.getName());
		studentTeacher.setEmail(teacher.getEmail());
		studentTeacher.setId(teacher.getId());
		currentStudent.getTeachers().add(studentTeacher);
		updateTeacherNotifications(studentTeacher);
		updateNotificationsCount(studentTeacher,lastSeen);
	}

	private void updateNotificationsCount(StudentTeacher studentTeacher, Date lastSeen) {
		int count = 0;
		for(Notification notification:studentTeacher.getNotifications()) {
			if(notification.getDate()!=null && notification.getDate().after(lastSeen)){
				count++;
			}
		}
		studentTeacher.setLatestCount(count);
		currentStudent.setTeacherNotificationsCount(currentStudent.getTeacherNotificationsCount() + count);
	}

	@Override
	public void updateTeacherNotifications(StudentTeacher studentTeacher) {
		List<Notification> notifications = studentDao.getTeacherNotifications(studentTeacher.getId(),prepareDepartmentNames(),currentStudent.getYear());
		studentTeacher.setNotifications(notifications);
		if(CollectionUtils.isEmpty(studentTeacher.getNotifications())) {
			return;
		}
	}

	@Override
	public void updateStudentToTeacherMapping(StudentTeacher studentTeacher) {
		StudentToTeacherMapping mapping = new StudentToTeacherMapping();
		mapping.setStudentId(currentStudent.getId());
		mapping.setTeacherId(studentTeacher.getId());
		mapping.setLastSeen(new Date());
		studentDao.updateTeacherMapping(mapping);
	}
	
	@Override
	public void updateStudentToDepartmentMapping(StudentDepartment studentDepartment) {
		StudentToDepartmentMapping mapping = new StudentToDepartmentMapping();
		mapping.setStudentId(currentStudent.getId());
		mapping.setDepartmentId(studentDepartment.getId());
		mapping.setLastSeen(new Date());
		studentDao.updateDepartmentMapping(mapping);
	}

	private List<Integer> prepareDepartmentNames() {
		List<Integer> names = new ArrayList<Integer>();
		for(StudentDepartment studentDepartment:currentStudent.getDepartments()) {
			names.add(studentDepartment.getId());
		}
		return names;
	}

	@Override
	public void prepareStudentDepartments() {
		currentStudent.setDepartmentNotificationsCount(0);
		currentStudent.setDepartments(new ArrayList<StudentDepartment>());
		List<StudentToDepartmentMapping> mappings = studentDao.getDepartmentMappings(currentStudent.getId());
		for(StudentToDepartmentMapping mapping:mappings) {
			Department department = collegeDao.getDepartment(mapping.getDepartmentId());
			prepareDepartmentNotification(department,mapping.getLastSeen());
		}
	}

	private void prepareDepartmentNotification(Department department, Date date) {
		StudentDepartment studentDepartment = new StudentDepartment();
		studentDepartment.setName(department.getName());
		studentDepartment.setId(department.getId());
		if(department!=null) {
			currentStudent.getDepartments().add(studentDepartment);
		}
		updateDepartmentNotifications(studentDepartment);
		updateDepartmentNotificationsCount(studentDepartment,date);
	}

	private void updateDepartmentNotificationsCount(StudentDepartment studentDepartment, Date lastSeen) {
		int count = 0;
		for(Notification notification:studentDepartment.getNotifications()) {
			if(notification.getDate()!=null && (lastSeen == null || notification.getDate().after(lastSeen))){
				count++;
			}
		}
		studentDepartment.setLatestCount(count);
		currentStudent.setDepartmentNotificationsCount(currentStudent.getDepartmentNotificationsCount() + count);
	}

	@Override
	public void updateDepartmentNotifications(StudentDepartment studentDepartment) {
		List<Notification> departmentNotifications = studentDao.getDepartmentNotifications(studentDepartment.getId(),currentStudent.getYear());
		studentDepartment.setNotifications(departmentNotifications);
	}

	@Override
	public boolean registerStudent(com.resoneuronance.campus.web.domain.Student inputStudent,String collegeName,String departmentName) {
		com.resoneuronance.campus.web.domain.Student student = studentDao.getStudent(inputStudent.getEmail());
		if(student==null) {
			return false;
		}
		College college = studentDao.getCollege(student.getCollegeId());
		if(StringUtils.equals(college.getName(), collegeName)) {
			Department department = studentDao.getDepartment(departmentName,college.getId());
			student.setDepartmentId(department.getId());
			student.setPassword(inputStudent.getPassword());
			studentDao.updateStudent(student);
			tagDepartment(department.getId());
			return true;
		}
		return false;
	}

	@Override
	public List<Teacher> getCurrentTeachers() {
		List<Teacher> teachers = new ArrayList<Teacher>();
		if(currentStudent == null || currentStudent.getCollege() == null) {
			return teachers;
		}
		teachers = studentDao.getAllTeachers(currentStudent.getCollege().getId());
		List<Teacher> untaggedTeachers = new ArrayList<Teacher>();
		List<StudentToTeacherMapping> mappings = studentDao.getTeacherMappings(currentStudent.getId());
		for(Teacher t:teachers) {
			boolean isTeacherTagged = false;
			for(StudentToTeacherMapping mapping:mappings) {
				if(mapping.getTeacherId() == t.getId()) {
					isTeacherTagged = true;
					break;
				}
			}
			if(!isTeacherTagged) {
				untaggedTeachers.add(t);
			}
			
		}
		return untaggedTeachers;
	}

	@Override
	public void tagTeachers(int[] teacherIds) {
		for (int i = 0; i < teacherIds.length; i++) {
			StudentToTeacherMapping mapping = new StudentToTeacherMapping();
			mapping.setStudentId(currentStudent.getId());
			mapping.setTeacherId(teacherIds[i]);
			studentDao.addTeacherMapping(mapping);
		}
	}

	@Override
	public StudentTeacher getNotifications(int teacherId) {
		return null;
	}

	@Override
	public List<Department> getCurrentDepartments() {
		List<Department> departments = new ArrayList<Department>();
		if(currentStudent == null || currentStudent.getCollege() == null) {
			return departments;
		}
		departments = studentDao.getAllDepartments(currentStudent.getCollege().getId());
		List<Department> untaggedDepartments = new ArrayList<Department>();
		List<StudentToDepartmentMapping> mappings = studentDao.getDepartmentMappings(currentStudent.getId());
		for(Department t:departments) {
			boolean isDepartmentTagged = false;
			for(StudentToDepartmentMapping mapping:mappings) {
				if(mapping.getDepartmentId()  == t.getId()) {
					isDepartmentTagged = true;
					break;
				}
			}
			if(!isDepartmentTagged) {
				untaggedDepartments.add(t);
			}
			
		}
		return untaggedDepartments;
	}

	@Override
	public void tagDepartments(int[] departmentIds) {
		for (int i = 0; i < departmentIds.length; i++) {
			int departmentId = departmentIds[i];
			tagDepartment(departmentId);
		}
	}

	private void tagDepartment(int departmentId) {
		StudentToDepartmentMapping mapping = new StudentToDepartmentMapping();
		mapping.setStudentId(currentStudent.getId());
		mapping.setDepartmentId(departmentId);
		studentDao.addDepartmentMapping(mapping);
	}

	@Override
	public void prepareCurrentStudent(Student student) {
		com.resoneuronance.campus.web.domain.Student st = studentDao.getStudent(student.getEmail());
		if(student.getCollege() == null) {
			return;
		}
		prepareStudent(student.getCollege(), st);
	}

}
