package learning.hibernate.relations.bydirectional.manyToMany;

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
    store.setName("Store1");
    store.setProducts(products);

    Store store2 = new Store();
    store2.setName("Store2");
    store2.setProducts(products);

    Set<Store> stores = new HashSet<Store>();
    stores.add(store);
    stores.add(store2);
    
    product1.setStores(stores);
    product2.setStores(stores);

    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(product1);
    session.save(product2);
    session.save(store);
    session.save(store2);
    session.getTransaction().commit();
    session.close();
  }
}
