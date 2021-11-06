package learning.hibernate.inheritance.joined;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class JoinedClassMain {

  public static void main(String[] args) {

    Publication publication1 = new Publication();
    publication1.setTitle("Manoj in Action --> Publication");

    Book book = new Book();
    book.setPages(2);
    book.setTitle("Manoj in Action --> Book");
    book.setVersion(1);

    BlogPost blogPost = new BlogPost();
    blogPost.setTitle("Manoj in Action --> Blog Post");
    blogPost.setVersion(1);
    blogPost.setUrl("some url");


    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(publication1);
    session.save(blogPost);
    session.save(book);
    session.getTransaction().commit();
    session.close();

  }

}
