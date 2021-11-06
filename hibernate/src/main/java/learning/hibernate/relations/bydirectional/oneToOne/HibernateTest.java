package learning.hibernate.relations.bydirectional.oneToOne;

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

    Passport passport = new Passport();
    passport.setName("Vehicle1");
    passport.setUser(user);
    
    user.setPassport(passport);


    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(passport);
    session.save(user);
    session.getTransaction().commit();
    session.close();
  }
}
