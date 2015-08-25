package com.resoneuronance.campus.web.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Notification;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.StudentRegID;
import com.resoneuronance.campus.web.domain.StudentToTeacherMapping;
import com.resoneuronance.campus.web.domain.Teacher;
import com.resoneuronance.campus.web.domain.TeacherToDepartmentMapping;

public class TeacherDAOImpl implements TeacherDAO {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Teacher getTeacher(String teacherEmail) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Teacher where email=:teacher_email");
		query.setParameter("teacher_email", teacherEmail);
		List<Teacher> teachers = query.list();
		session.close();
		if (CollectionUtils.isEmpty(teachers)) {
			return null;
		}
		return teachers.get(0);
	}

	@Override
	public College getCollege(int collegeId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from College where id=:college_id");
		query.setParameter("college_id", collegeId);
		List<College> colleges = query.list();
		session.close();
		if (CollectionUtils.isEmpty(colleges)) {
			return null;
		}
		return colleges.get(0);
	}

	@Override
	public void updateTeacher(Teacher teacher) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update Teacher SET password=:pwd where id=:teacher_id");
		query.setParameter("teacher_id", teacher.getId());
		query.setParameter("pwd", teacher.getPassword());
		query.executeUpdate();
		session.close();
	}

	@Override
	public void addDepartmentMapping(TeacherToDepartmentMapping mapping) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(mapping);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<TeacherToDepartmentMapping> getDepartments(int teacherId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from TeacherToDepartmentMapping where teacherId=:teacher_id");
		query.setParameter("teacher_id", teacherId);
		List<TeacherToDepartmentMapping> mappings = query.list();
		session.close();
		return mappings;
	}

	@Override
	public void addNotification(Notification notification) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(notification);
		tx.commit();
		session.close();
	}

	@Override
	public List<Notification> getNotifications(int teacherId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Notification where teacherId=:teacher_id");
		query.setParameter("teacher_id", teacherId);
		List<Notification> mappings = query.list();
		session.close();
		return mappings;
	}

	@Override
	public void deleteNotification(int id) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("delete from Notification where id=:notification_id");
		query.setParameter("notification_id", id);
		query.executeUpdate();
		session.close();
	}

	@Override
	public Notification getNotification(int id) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Notification where id=:notification_id");
		query.setParameter("notification_id", id);
		List<Notification> notifications = query.list();
		session.close();
		if(CollectionUtils.isNotEmpty(notifications)) {
			return notifications.get(0);
		}
		return null;
	}

	@Override
	public Department getDepartment(int id) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Department where id=:dept_id");
		query.setParameter("dept_id", id);
		List<Department> departments = query.list();
		session.close();
		if(CollectionUtils.isNotEmpty(departments)) {
			return departments.get(0);
		}
		return null;
	}

	@Override
	public List<StudentToTeacherMapping> getMappedStudents(int teacherId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from StudentToTeacherMapping where teacherId=:teacher_id");
		query.setParameter("teacher_id", teacherId);
		List<StudentToTeacherMapping> mappings = new ArrayList<StudentToTeacherMapping>();
		mappings = query.list();
		session.close();
		return mappings;
	}
	
	@Override
	public List<StudentRegID> getStudentRegId(int studentId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from StudentRegID where studentId=:student_id");
		query.setParameter("student_id", studentId);
		List<StudentRegID> regIds = new ArrayList<StudentRegID>();
		regIds = query.list();
		session.close();
		return regIds;
	}
	
	@Override
	public Student getFilteredStudent(Notification notification,int studentId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Student where id=:student_id AND departmentId=:dept_id AND year=:year");
		query.setParameter("student_id", studentId);
		query.setParameter("dept_id", notification.getDepartmentId());
		query.setParameter("year", notification.getYear());
		List<Student> students = query.list();
		if(CollectionUtils.isEmpty(students)) {
			return null;
		}
		return students.get(0);
	}

}
