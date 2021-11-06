package learning.hibernate.hql;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernatePositionalParameter {
  public static void main(String[] args) {
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query query = session.createQuery("from USER_HQL where userId > ? and userName = ?");
    query.setInteger(0, 1);
    query.setString(1, "User : 0");
    
    List<User> users = (List<User>) query.list();
    session.close();

    for (User user : users) {
      System.out.println(user.getUserName());
    }
  }
}
