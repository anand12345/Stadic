package com.resoneuronance.campus.web.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;

public class CollegeDAOImpl implements CollegeDAO {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<College> getAllColleges() {
		Session session = this.sessionFactory.openSession();
		List<College> colleges = session.createQuery("from College").list();
		System.out.println("No of colleges:" + colleges.size());
		//System.out.println("College name:" + colleges.get(0).getName());
		session.close();
		return colleges;
	}
	
	@Override
    public void save(College college) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(college);
        tx.commit();
        session.close();
    }

	@SuppressWarnings("unchecked")
	@Override
	public College getCollege(String collegeName) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from College C where C.name=:college_name");
		query.setParameter("college_name", collegeName);
		List<College> colleges = query.list();
		session.close();
		if(CollectionUtils.isNotEmpty(colleges)) {
			return colleges.get(0);
		}
		return null;
	}

	@Override
	public void addDepartment(Department department) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(department);
        tx.commit();
        session.close();
	}
	
	@Override
	public void addDepartments(List<Department> departments) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        for(Department dept:departments) {
        	session.persist(dept);
        }
        
        tx.commit();
        session.close();
	}

	@Override
	public List<Department> getAllDepartments(int collegeId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Department D where D.collegeId=:id");
		query .setParameter("id", collegeId);
		List<Department> departments = query.list();
		System.out.println("No of Departments:" + departments.size());
		//System.out.println("College name:" + colleges.get(0).getName());
		session.close();
		return departments;
	}

	@Override
	public void deleteDepartment(int departmentId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("DELETE from Department D where D.id=:id");
		query .setParameter("id", departmentId);
		int result = query.executeUpdate();
		//System.out.println("College name:" + colleges.get(0).getName());
		session.close();
	}

	@Override
	public List<Teacher> getAllTeachers(int collegeId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Teacher T where T.collegeId=:id");
		query .setParameter("id", collegeId);
		List<Teacher> teachers = query.list();
		System.out.println("No of Departments:" + teachers.size());
		session.close();
		return teachers;
	}

	@Override
	public void addTeachers(List<Teacher> teachers) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        for(Teacher teacher:teachers) {
        	session.persist(teacher);
        }
        
        tx.commit();
        session.close();
		
	}

	@Override
	public void addTeacher(Teacher teacher) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(teacher);
        tx.commit();
        session.close();
	}

	@Override
	public void deleteTeacher(int teacherId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("DELETE from Teacher T where T.id=:id");
		query.setParameter("id", teacherId);
		query.executeUpdate();
		session.close();
		
	}

	@Override
	public List<Student> getAllStudents(int collegeId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Student T where T.collegeId=:id");
		query .setParameter("id", collegeId);
		List<Student> students = query.list();
		System.out.println("No of Students:" + students.size());
		session.close();
		return students;
	}

	@Override
	public void addStudents(List<Student> students) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        for(Student student:students) {
        	session.persist(student);
        }
        
        tx.commit();
        session.close();
		
	}

	@Override
	public void addStudent(Student student) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(student);
        tx.commit();
        session.close();		
	}

	@Override
	public void deleteStudent(int studentId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("DELETE from Student T where T.id=:id");
		query.setParameter("id", studentId);
		query.executeUpdate();
		session.close();
	}
	
	@Override
	public Student getStudent(int studentId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Student D where D.id=:id");
		query .setParameter("id", studentId);
		List<Student> students = query.list();
		session.close();
		if(CollectionUtils.isEmpty(students)) {
			return new Student();
		}
		return students.get(0);
	}

	@Override
	public void updateStudent(Student student) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update Student D SET D.name=:name,D.email=:email where D.id=:id");
		query .setParameter("id", student.getId());
		query.setParameter("name", student.getName());
		query.setParameter("email", student.getEmail());
		query.executeUpdate();
		session.close();
	}

	@Override
	public Department getDepartment(int departmentId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Department D where D.id=:id");
		query .setParameter("id", departmentId);
		List<Department> departments = query.list();
		session.close();
		if(CollectionUtils.isEmpty(departments)) {
			return new Department();
		}
		return departments.get(0);
	}

	@Override
	public void updateDepartment(Department department) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update Department D SET D.name=:name where D.id=:id");
		query .setParameter("id", department.getId());
		query.setParameter("name", department.getName());
		query.executeUpdate();
		session.close();		
	}

	@Override
	public Teacher getTeacher(int teacherId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Teacher D where D.id=:id");
		query .setParameter("id", teacherId);
		List<Teacher> teachers = query.list();
		session.close();
		if(CollectionUtils.isEmpty(teachers)) {
			return new Teacher();
		}
		return teachers.get(0);
	}

	@Override
	public void updateTeacher(Teacher teacher) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update Teacher D SET D.name=:name,D.email=:email where D.id=:id");
		query .setParameter("id", teacher.getId());
		query.setParameter("name", teacher.getName());
		query.setParameter("email", teacher.getEmail());
		query.executeUpdate();
		session.close();		
	}
	

}
