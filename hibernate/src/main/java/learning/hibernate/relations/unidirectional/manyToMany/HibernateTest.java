package learning.hibernate.relations.unidirectional.manyToMany;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
  public static void main(String[] args) {

    Product product1 = new Product();
    product1.setName("product");
    product1.setDescription("some description");
    product1.setCreationDate(new Date());

    Product product2 = new Product();
    product2.setName("product");
    product2.setDescription("some description");
    product2.setCreationDate(new Date());

    Set<Product> products = new HashSet<Product>();
    products.add(product1);
    products.add(product2);

    Store store = new Store();
    store.setName("Vehicle1");
    store.setProducts(products);

    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(product1);
    session.save(product2);
    session.save(store);
    session.getTransaction().commit();
    session.close();
  }
}
