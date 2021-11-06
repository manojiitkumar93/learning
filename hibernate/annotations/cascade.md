Lest understand CASCADE functionality with OnToMany relationship.<br>

```
@Entity(name = "VEHICLE_CASCADE")
public class Vehicle {

  private String name;
  private Integer id;

  @Column(name = "NAME")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
```

```
@Entity(name = "USER_CASCADE")
public class User {

  private Integer userId;
  private String userName;
  private String description;
  private Date creationDate;

  private Collection<Vehicle> vehicles = new ArrayList();

  @OneToMany
  public Collection<Vehicle> getVehicles() {
    return vehicles;
  }

  public void setVehicles(Collection<Vehicle> vehicles) {
    this.vehicles = vehicles;
  }

  @Lob
  @Column(name = "DESCRIPTION")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Temporal(TemporalType.TIME)
  @Column(name = "CREATION_DATE")
  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

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
From the above two class, Vehicle and User are two defined entities. And User to Vehicle it is a **OneToMany**
relationship.<br>

```
public class HibernateTest {
	public static void main(String[] args) {

		User user = new User();
		user.setUserName("Manoj");
		user.setDescription("some description");
		user.setCreationDate(new Date());

		Vehicle vehicle = new Vehicle();
		vehicle.setName("Some vehicle name");
		
		user.getVehicles().add(vehicle);

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
}
```
Now if we execute the above main class, hibernate throws an exception saying..
```
 org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: learning.hibernate.cascade.Vehicle
```

Meaning --> Hibernate is asking to save vehicle entity explicitly.<br>
#### Why Hibernate is not saving the vehicle entities automatically while saving user entity?
If we observe even Vehicle is alo an Entity. Hibernate cannot make assumption and save the entity because
may be in some other cases we want to save it differently and you dont want to save these entities automatically.<br>

But the above Vehicle is a collection then it would have been saved automatically **(Meaning it is not an Entity
but an Embeddable object)**.

#### JPA Cascade Types..

The cascade types supported by the Java Persistence Architecture are as below:

**CascadeType.PERSIST** : cascade type `presist` means that save() or persist() operations cascade to related entities.<br>
<br>
**CascadeType.MERGE** : cascade type `merge` means that related entities are merged when the owning entity is merged. Merge inclus (Save, Update and Delete)<br>
<br>

**CascadeType.REFRESH** : cascade type `refresh` does the same thing for the refresh() operation.<br>
Sometimes, it is required to re-load an object and its collections at a time when the application database is modified with some external application or database triggers and thus corresponding hibernate entity in your becomes out of sync with its database representation.

In this case, one can use session.refresh() method to re-populate the entity with latest data available in database.<br>
<br>
**CascadeType.REMOVE** : cascade type `remove` removes all related entities association with this setting when the owning entity is deleted.<br>
<br>
**CascadeType.DETACH** : cascade type `detach` detaches all related entities if a “manual detach” occurs.<br>
<br>
**CascadeType.ALL** : cascade type `all` is shorthand for all of the above cascade operations.<br>
<br>

**NOTE:** `There is no default cascade type in JPA. By default no operations are cascaded.`<br>
<br>
The cascade configuration option accepts an array of CascadeTypes; thus, to include only refreshes and merges in the cascade operation for a 
One-to-Many relationship as in our example, you might see the following:
```
@OneToMany(cascade={CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
private Collection<Vehicle> vehicles;
```
Above cascading will cause Vehicle collection to be only merged and refreshed.<br>
### Hibernate Cascade Types
Now lets understand what is cascade in hibernate in which scenario we use it.<br>
<br>
Apart from JPA provided cascade types, there is one more cascading operation in hibernate which is not part of the normal set above discussed, called **“orphan removal“**. 
This removes an owned object from the database when it’s removed from its owning relationship.<br>
<br>
Let’s understand with an example. In our User and Vehicle entity example,
I have updated them as below and have mentioned **“orphanRemoval = true”** on Vehicles.
It essentially means that whenever I will remove an **‘Vehicle from Vehicles collection’ (which means I am removing the relationship between that Vehicle and User);**
the Vehicle entity which is not associated with any other User on database (i.e. orphan) should also be deleted.
* UserCascade.java
```
@Entity(name = "USER_CASCADE_ORPHAN")
public class UserCascade {

  private Integer userId;
  private String userName;
  private String description;
  private Date creationDate;

  private Collection<VehicleCascade> vehicles = new ArrayList();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy="userCascade")
  public Collection<VehicleCascade> getVehicles() {
    return vehicles;
  }

  public void setVehicles(Collection<VehicleCascade> vehicles) {
    this.vehicles = vehicles;
  }

  @Lob
  @Column(name = "DESCRIPTION")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Temporal(TemporalType.TIME)
  @Column(name = "CREATION_DATE")
  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

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

* VehicleCascade.java

```
@Entity(name = "VEHICLE_CASCADE_ORPHAN")
public class VehicleCascade {

  private String name;
  private Integer id;
  private UserCascade userCascade;

  @ManyToOne
  @JoinColumn(name="USER_ID")
  public UserCascade getUserCascade() {
    return userCascade;
  }

  public void setUserCascade(UserCascade userCascade) {
    this.userCascade = userCascade;
  }

  @Column(name = "NAME")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}

```

* HIbernateTest.java
```
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

Output:

Hibernate: insert into USER_CASCADE_ORPHAN (CREATION_DATE, DESCRIPTION, USER_NAME, USER_ID) values (?, ?, ?, ?)
Hibernate: insert into VEHICLE_CASCADE_ORPHAN (NAME, USER_ID, id) values (?, ?, ?)
Hibernate: insert into VEHICLE_CASCADE_ORPHAN (NAME, USER_ID, id) values (?, ?, ?)
Hibernate: insert into VEHICLE_CASCADE_ORPHAN (NAME, USER_ID, id) values (?, ?, ?)
Hibernate: select usercascad0_.USER_ID as USER_ID1_0_0_, usercascad0_.CREATION_DATE as CREATION2_0_0_, usercascad0_.DESCRIPTION as DESCRIPT3_0_0_, usercascad0_.USER_NAME as USER_NAM4_0_0_ from USER_CASCADE_ORPHAN usercascad0_ where usercascad0_.USER_ID=?
Hibernate: select vehicles0_.USER_ID as USER_ID3_1_0_, vehicles0_.id as id1_1_0_, vehicles0_.id as id1_1_1_, vehicles0_.NAME as NAME2_1_1_, vehicles0_.USER_ID as USER_ID3_1_1_ from VEHICLE_CASCADE_ORPHAN vehicles0_ where vehicles0_.USER_ID=?
Step 1 : 3
Step 2 : 2
Hibernate: delete from VEHICLE_CASCADE_ORPHAN where id=?
Hibernate: select usercascad0_.USER_ID as USER_ID1_0_0_, usercascad0_.CREATION_DATE as CREATION2_0_0_, usercascad0_.DESCRIPTION as DESCRIPT3_0_0_, usercascad0_.USER_NAME as USER_NAM4_0_0_ from USER_CASCADE_ORPHAN usercascad0_ where usercascad0_.USER_ID=?
Hibernate: select vehicles0_.USER_ID as USER_ID3_1_0_, vehicles0_.id as id1_1_0_, vehicles0_.id as id1_1_1_, vehicles0_.NAME as NAME2_1_1_, vehicles0_.USER_ID as USER_ID3_1_1_ from VEHICLE_CASCADE_ORPHAN vehicles0_ where vehicles0_.USER_ID=?
Step 3 : 2
Hibernate: select vehiclecas0_.id as id1_1_, vehiclecas0_.NAME as NAME2_1_, vehiclecas0_.USER_ID as USER_ID3_1_ from VEHICLE_CASCADE_ORPHAN vehiclecas0_
Step 4 : 2

```

#### NOTE
Cascading only makes sense only for Parent – Child associations (the Parent entity state transition being cascaded to its Child entities). Cascading from Child to Parent is not very useful and usually, it’s a mapping code smell.








