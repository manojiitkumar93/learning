package learning.hibernate.relations.unidirectional.manyToOne;

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
