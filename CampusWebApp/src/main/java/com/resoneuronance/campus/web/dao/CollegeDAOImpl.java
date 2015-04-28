package com.resoneuronance.campus.web.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;

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


/*	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"spring.xml");

		CollegeDAO collegeDAO = context.getBean(CollegeDAO.class);

		College college = new College();
		college.setName("IIN");
		collegeDAO.save(college);

		System.out.println("saved!");
		
		List<College> list = collegeDAO.getAllColleges();

		for (College p : list) {
			System.out.println("Person List::" + p);
		}
		// close resources
		context.close();
	}
*/
}
