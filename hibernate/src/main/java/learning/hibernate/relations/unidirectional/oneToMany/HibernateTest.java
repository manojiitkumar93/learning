package learning.hibernate.relations.unidirectional.oneToMany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    Vehicle vehicle2 = new Vehicle();
    vehicle2.setName("Vehicle2");

    List<Vehicle> vehicles = new ArrayList<Vehicle>();
    vehicles.add(vehicle2);
    vehicles.add(vehicle1);
    user.setVehicles(vehicles);



    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(vehicle1);
    session.save(vehicle2);
    session.save(user);
    session.getTransaction().commit();
    session.close();
  }
}
