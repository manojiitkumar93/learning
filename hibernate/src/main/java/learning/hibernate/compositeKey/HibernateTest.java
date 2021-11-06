package learning.hibernate.compositeKey;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
  public static void main(String[] args) {
    
    UserId userId = new UserId();

    User user = new User();
    user.setLastName("Kumar");
    user.setFirstName("Manoj");
    user.setDescription("some description");
    user.setCreationDate(new Date());
    
    userId.setFirstName(user.getFirstName());
    userId.setLastName(user.getLastName());
    
    user.setUserId(userId);

    Vehicle vehicle1 = new Vehicle();
    vehicle1.setName("Vehicle1");
    vehicle1.setUser(user);

    Vehicle vehicle2 = new Vehicle();
    vehicle2.setName("Vehicle2");
    vehicle2.setUser(user);


    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(user);
    session.save(vehicle1);
    session.save(vehicle2);
    session.getTransaction().commit();
    session.close();
  }
}
