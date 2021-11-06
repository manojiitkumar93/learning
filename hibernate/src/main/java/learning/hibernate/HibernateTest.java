package learning.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
	public static void main(String[] args) {

		UserInfo userInfo = new UserInfo();
		userInfo.setUserName("Manoj");
		userInfo.setDescription("some description");
		userInfo.setCreationDate(new
		    Date());

		Address officeAddress = new Address();
		officeAddress.setCity("city1");
		officeAddress.setState("state1");
		officeAddress.setStreet("street1");
		officeAddress.setPinCode("pincode1");
		userInfo.setOfficeAddress(officeAddress);
		
		Address homeAddress = new Address();
		homeAddress.setCity("city_home");
		homeAddress.setState("state_home");
		homeAddress.setStreet("street_home");
		homeAddress.setPinCode("pincode_home");
		userInfo.setHomeAddress(homeAddress);

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(userInfo);
		session.getTransaction().commit();
		session.close();

		userInfo = null;
		session = sessionFactory.openSession();
		userInfo = session.get(UserInfo.class, 1);
		System.out.println("retrived userInfo with UserName:" + userInfo.getUserName());
	}
}
