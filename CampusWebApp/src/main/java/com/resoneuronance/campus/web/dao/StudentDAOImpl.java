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
import com.resoneuronance.campus.web.domain.StudentToDepartmentMapping;
import com.resoneuronance.campus.web.domain.StudentToTeacherMapping;
import com.resoneuronance.campus.web.domain.Teacher;

public class StudentDAOImpl implements StudentDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Student getStudent(String studentEmail) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Student S where S.email=:student_email");
		query.setParameter("student_email", studentEmail);
		List<Student> students = query.list();
		session.close();
		if (CollectionUtils.isEmpty(students)) {
			return null;
		}
		return students.get(0);
	}

	@Override
	public void updateStudent(Student student) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update Student SET password=:pwd,departmentId=:dept_id where id=:student_id");
		query.setParameter("student_id", student.getId());
		query.setParameter("pwd", student.getPassword());
		query.setParameter("dept_id", student.getDepartmentId());
		query.executeUpdate();
		session.close();
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
	public Department getDepartment(String departmentName, int id) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Department where name=:department_name AND collegeId=:college_id");
		query.setParameter("college_id", id);
		query.setParameter("department_name", departmentName);
		List<Department> departments = query.list();
		session.close();
		if (CollectionUtils.isEmpty(departments)) {
			return null;
		}
		return departments.get(0);
	}

	@Override
	public Department getDepartment(int departmentId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Department where id=:dept_id");
		query.setParameter("dept_id", departmentId);
		List<Department> departments = query.list();
		session.close();
		if (CollectionUtils.isEmpty(departments)) {
			return null;
		}
		return departments.get(0);
	}

	@Override
	public List<Notification> getDepartmentNotifications(int id, String year) {
		List<Notification> notifications = new ArrayList<Notification>();
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Notification where departmentId=:dept_id AND year=:year AND type=:dept_typ ORDER BY date DESC");
		query.setParameter("dept_id", id);
		query.setParameter("year", year);
		query.setParameter("dept_typ", "Department");
		notifications = query.list();
		session.close();
		return notifications;
	}

	@Override
	public List<Teacher> getAllTeachers(int id) {
		List<Teacher> teachers = new ArrayList<Teacher>();
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Teacher where collegeId=:college_id");
		query.setParameter("college_id", id);
		teachers = query.list();
		session.close();
		return teachers;
	}

	@Override
	public void addTeacherMapping(StudentToTeacherMapping mapping) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(mapping);
		tx.commit();
		session.close();
	}

	@Override
	public List<StudentToTeacherMapping> getTeacherMappings(int id) {
		List<StudentToTeacherMapping> teachers = new ArrayList<StudentToTeacherMapping>();
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from StudentToTeacherMapping where studentId=:id");
		query.setParameter("id", id);
		teachers = query.list();
		session.close();
		return teachers;
	}

	@Override
	public List<Notification> getTeacherNotifications(int id, List<Integer> departmentIds, String year) {
		List<Notification> notifications = new ArrayList<Notification>();
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Notification where teacherId=:id AND year=:year AND departmentId IN (:ids) AND type=:teacher_typ ORDER BY date DESC");
		query.setParameter("id", id);
		query.setParameter("teacher_typ", "Teacher");
		query.setParameterList("ids", departmentIds);
		query.setParameter("year", year);
		notifications = query.list();
		session.close();
		return notifications;
	}

	@Override
	public void updateTeacherMapping(StudentToTeacherMapping mapping) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update StudentToTeacherMapping SET lastSeen=:last_seen where teacherId=:teacher_id AND studentId=:student_id");
		query.setParameter("student_id", mapping.getStudentId());
		query.setParameter("teacher_id", mapping.getTeacherId());
		query.setParameter("last_seen", mapping.getLastSeen());
		query.executeUpdate();
		session.close();
	}

	@Override
	public List<Department> getAllDepartments(int id) {
		List<Department> departments = new ArrayList<Department>();
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Department where collegeId=:college_id");
		query.setParameter("college_id", id);
		departments = query.list();
		session.close();
		return departments;
	}
	
	@Override
	public List<StudentToDepartmentMapping> getDepartmentMappings(int id) {
		List<StudentToDepartmentMapping> departments = new ArrayList<StudentToDepartmentMapping>();
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from StudentToDepartmentMapping where studentId=:id");
		query.setParameter("id", id);
		departments = query.list();
		session.close();
		return departments;
	}
	
	@Override
	public void addDepartmentMapping(StudentToDepartmentMapping mapping) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(mapping);
		tx.commit();
		session.close();
	}
	
	@Override
	public void updateDepartmentMapping(StudentToDepartmentMapping mapping) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update StudentToDepartmentMapping SET lastSeen=:last_seen where departmentId=:dept_id AND studentId=:student_id");
		query.setParameter("student_id", mapping.getStudentId());
		query.setParameter("dept_id", mapping.getDepartmentId());
		query.setParameter("last_seen", mapping.getLastSeen());
		query.executeUpdate();
		session.close();		
	}

	@Override
	public void addRegId(StudentRegID studentRegID) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(studentRegID);
		tx.commit();
		session.close();
	}

}
