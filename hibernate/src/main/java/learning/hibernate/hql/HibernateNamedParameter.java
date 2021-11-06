package learning.hibernate.hql;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernateNamedParameter {
  public static void main(String[] args) {
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query query = session.createQuery("from USER_HQL where userId =: id and userName =:name");
    query.setParameter("id", 1);
    query.setParameter("name", "User : 0");

    List<User> users = (List<User>) query.list();
    session.close();

    for (User user : users) {
      System.out.println(user.getUserName());
    }
  }

}
