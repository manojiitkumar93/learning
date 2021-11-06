### HQL (Hibernate Query Language)
HQL is similar to SQL. In SQL we talk about tables, entries in tables but in HQL we tak about objects. Lets understand with examples..<br>

**User details in the DB**
```
mysql> select * from USER_HQL;
+---------+-----------+
| USER_ID | USER_NAME |
+---------+-----------+
|      21 | User : 0  |
|      22 | User : 1  |
|      23 | User : 2  |
|      24 | User : 3  |
|      25 | User : 4  |
|      26 | User : 5  |
|      27 | User : 6  |
|      28 | User : 7  |
|      29 | User : 8  |
|      30 | User : 9  |
+---------+-----------+

```

**Query to retrieve the Users from the DB**
```
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query query = session.createQuery("from USER_HQL");
    List<User> users = (List<User>) query.list();
    session.close();

    for (User user : users) {
      System.out.println(user.getUserName());
    }
    
OutPut : 

Hibernate: select user0_.USER_ID as USER_ID1_0_, user0_.USER_NAME as USER_NAM2_0_ from USER_HQL user0_
User : 0
User : 1
User : 2
User : 3
User : 4
User : 5
User : 6
User : 7
User : 8
User : 9
```
If you see from the above query results returned by hibernate, it gives us the whole User details. What if I want to only..
* Particular columns of the User
* What if I dont want to retrive all the user's but wanted to get the Pagination kind of results

#### How does hibernate support the Pagination
```
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query query = session.createQuery("from USER_HQL");
    query.setFirstResult(5);
    query.setMaxResults(3);
    List<User> users = (List<User>) query.list();
    session.close();

    for (User user : users) {
      System.out.println(user.getUserName());
    }
  }
  
OutPut:

Hibernate: select user0_.USER_ID as USER_ID1_0_, user0_.USER_NAME as USER_NAM2_0_ from USER_HQL user0_ limit ?, ?
User : 5
User : 6
User : 7

```
* **query.setFirstResult(5)** -> tells what is the start of my results that i am interested in. This starts from row number 5.
* **query.setMaxResults(3)**  -> tells what is the maximum numbers of results it needs to pull. In this case it is 3 results.
* If we check the query executed by the hibernate it is similar to **select * from USER_HQL limit 5,3;**

#### SQL Injection
Injecting the value to the SQL statement. SQL injection refers to the act of someone inserting a MySQL statement to be run on 
database without our knowledge. Injection usually occurs when you ask a user for input, like their name, and instead of a name 
they give us a MySQL statement that you will unknowingly run on your database.<br>
<br>
** Example:**
```
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Integer userId = 5;
    Query query = session.createQuery("from USER_HQL where userId = " + userId);
    List<User> users = (List<User>) query.list();
    session.close();

    for (User user : users) {
      System.out.println(user.getUserName());
    }
  }
  
OutPut...

Hibernate: select user0_.USER_ID as USER_ID1_0_, user0_.USER_NAME as USER_NAM2_0_ from USER_HQL user0_ where user0_.USER_ID=5
User : 4
```

#### Hibernate parameter binding
There are two ways to parameter binding :
* Positional parameters binding.
* Named parameters binding.

##### Positional parameters binding (No more supported from HIbernate 5.2)
HIbernate use question mark (?) to define a parameter, and you have to set your parameter according to the position sequence. This is no more supported from Hibernate 5.2<br>
<br>
**Example..**
```
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

OutPut..
Exception in thread "main" java.lang.IllegalArgumentException: org.hibernate.QueryException: Legacy-style query parameters (`?`) are no longer supported; use JPA-style ordinal parameters (e.g., `?1`) instead : from learning.hibernate.hql.User where userId > ? and userName = ? 
[from learning.hibernate.hql.User where userId > ? and userName = ?]
```
##### Named parameters binding
This is the most common and user friendly way. It use colon followed by a parameter name (:example) to define a named parameter
```
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
  
OutPut...

Hibernate: select user0_.USER_ID as USER_ID1_0_, user0_.USER_NAME as USER_NAM2_0_ from USER_HQL user0_ where user0_.USER_ID=? and user0_.USER_NAME=?
User : 0
```

#### Hibernate Named Queries
Often times, developer like to put HQL string literals scatter all over the Java code, 
this method is hard to maintain and look ugly. Fortunately, Hibernate come out a technique called **“named queries”** , 
it lets developer to put all HQL into the **XML mapping** file or via **annotation**.<br>

##### Named Queries with Annotation (@NamedQuery)
**User.java**
```
@NamedQueries({@NamedQuery(name = "UserDetails.byId", query = "from USER_HQL where userId=:id"),
    @NamedQuery(name = "UserDetails.byName", query = "from USER_HQL where userName=:name")})
@Entity(name = "USER_HQL")
public class User {

  private Integer userId;
  private String userName;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "USER_ID")
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Column(name = "USER_NAME")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
```

**HibernateNamedQueries.java**

```
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
  
OutPut ...

Hibernate: select user0_.USER_ID as USER_ID1_0_, user0_.USER_NAME as USER_NAM2_0_ from USER_HQL user0_ where user0_.USER_ID=?
User : 0
```

##### Named Queries with Annotation (@NamedNativeQuery)
**User.java**
```
@NamedQueries({@NamedQuery(name = "UserDetails.byId", query = "from USER_HQL where userId=:id"),
    @NamedQuery(name = "UserDetails.byName", query = "from USER_HQL where userName=:name")})
@Entity(name = "USER_HQL")
public class User {

  private Integer userId;
  private String userName;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "USER_ID")
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Column(name = "USER_NAME")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}

```

**HibernateNamedNativeQueries.java**
```
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query query = (Query) session.getNamedNativeQuery("UderDetailsNative.byId");
    query.setParameter("id", 1);
    List<User> users = (List<User>) query.list();
    session.close();

    for (User user : users) {
      System.out.println(user.getUserName());
    }
  }

OutPut..

Hibernate: select * from USER_HQL where USER_ID=?
User : 0

```


