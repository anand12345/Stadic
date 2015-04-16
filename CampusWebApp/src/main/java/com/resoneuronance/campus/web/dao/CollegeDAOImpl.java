package com.resoneuronance.campus.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.resoneuronance.campus.web.domain.College;

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
