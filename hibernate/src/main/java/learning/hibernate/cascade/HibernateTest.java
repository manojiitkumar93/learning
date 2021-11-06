package learning.hibernate.cascade;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
	public static void main(String[] args) {

		User user = new User();
		user.setUserName("Manoj");
		user.setDescription("some description");
		user.setCreationDate(new Date());

		Vehicle vehicle = new Vehicle();
		vehicle.setName("Some vehicle name");
		
		user.getVehicles().add(vehicle);

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
}
