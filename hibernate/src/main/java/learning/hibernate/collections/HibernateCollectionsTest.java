package learning.hibernate.collections;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateCollectionsTest {
	public static void main(String[] args) {

		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setUserName("Manoj");
		studentInfo.setDescription("some description");
		studentInfo.setCreationDate(new Date());

		Address address1 = new Address();
		address1.setCity("city1");
		address1.setState("state1");
		address1.setStreet("street1");
		address1.setPinCode("pincode1");

		Address address2 = new Address();
		address2.setCity("city_home");
		address2.setState("state_home");
		address2.setStreet("street_home");
		address2.setPinCode("pincode_home");
		
		studentInfo.getAddressSet().add(address1);
		studentInfo.getAddressSet().add(address2);

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(studentInfo);
		session.getTransaction().commit();
		session.close();
		
		studentInfo = null;
		session = sessionFactory.openSession();
		studentInfo = session.get(StudentInfo.class, 1);
		session.close();
		System.out.println(studentInfo.getAddressSet().size());
	}

}
