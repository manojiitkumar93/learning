package learning.hibernate.inheritance.mapperSuperClass;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MapperSuperClassMain {

  public static void main(String[] args) {

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
    session.save(book);
    session.save(blogPost);

    List<Book> books = session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    System.out.println(books);
    session.getTransaction().commit();
    session.close();

  }

}
