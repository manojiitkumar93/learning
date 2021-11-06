package learning.hibernate.cascade.orphan;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernateTest {
  public static void main(String[] args) {

    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    setUpData(sessionFactory);

    Session sessionOne = sessionFactory.openSession();
    Transaction transaction = sessionOne.beginTransaction();

    UserCascade user = (UserCascade) sessionOne.load(UserCascade.class, 1);

    // Verify there are 3 Vehicles
    System.out.println("Step 1 : " + user.getVehicles().size());

    // Remove an Vehicle from first position of collection
    user.getVehicles().remove(user.getVehicles().iterator().next());

    // Verify there are 2 Vehicles in collection
    System.out.println("Step 2 : " + user.getVehicles().size());

    transaction.commit();
    sessionOne.close();

    // In another session check the actual data in database
    Session sessionTwo = sessionFactory.openSession();
    sessionTwo.beginTransaction();

    UserCascade user11 = (UserCascade) sessionTwo.load(UserCascade.class, 1);
    // Verify there are 2 Vehicles now associated with User
    System.out.println("Step 3 : " + user11.getVehicles().size());

    // Verify there are 2 Vehicles in Account table
    Query query = sessionTwo.createQuery("from VEHICLE_CASCADE_ORPHAN");
    List<VehicleCascade> vehicles = query.list();
    System.out.println("Step 4 : " + vehicles.size());
    sessionTwo.close();
  }

  private static void setUpData(SessionFactory sessionFactory) {
    // Create Vehicle 1
    VehicleCascade vehicle1 = new VehicleCascade();
    vehicle1.setName("Some vehicle name1");

    // Create Vehicle 1
    VehicleCascade vehicle2 = new VehicleCascade();
    vehicle2.setName("Some vehicle name2");

    // Create Vehicle 1
    VehicleCascade vehicle3 = new VehicleCascade();
    vehicle3.setName("Some vehicle name3");

    // Create User
    UserCascade user = new UserCascade();
    user.setUserName("Manoj");
    user.setDescription("some description");
    user.setCreationDate(new Date());
    user.getVehicles().add(vehicle1);
    user.getVehicles().add(vehicle2);
    user.getVehicles().add(vehicle3);
    
    vehicle1.setUserCascade(user);
    vehicle2.setUserCascade(user);
    vehicle3.setUserCascade(user);

    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(user);
    session.getTransaction().commit();
    session.close();
  }
}
