```
@ElementCollection
```
* We can use the `@ElementCollection` annotation to store a list of values as an entity attribute without needing to model an additional entity.
* This annotation is placed on the collection reference in the entity class.
* The Entity and the basic type collection are mapped to two separate foreign/primary-key tables. One for the entity and other for basic collection values. The collection table has the foreign key pointing to the primary key of the entity.
* This is similar to `OneToMany` relation, except the target is a basic value instead of another entity.
* By default, JPA specific naming conventions are used for the mapping. To customize that, we can use `@CollectionTable` annotation along with `@ElementCollection`.

```
@Embeddable
public class Address {

	private String street;
	private String city;
	private String state;
	private String pinCode;

	@Column(name = "STREET_NAME")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "CITY_NAME")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATE_NAME")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "PIN_CODE")
	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
}
```

```
@Entity(name = "STUDENT_INFO")
public class StudentInfo {

	private Integer userId;
	private String userName;
	private String description;
	private Date creationDate;
	private Set<Address> addressSet = new HashSet();

	@ElementCollection
	@CollectionTable (name = "STUDENT_ADDRESS", joinColumns = @JoinColumn(name = "STUDENT_ID"))
	public Set<Address> getAddressSet() {
		return addressSet;
	}

	public void setAddressSet(Set<Address> addressSet) {
		this.addressSet = addressSet;
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

```
public class HibernateCollectionsTest {
	public static void main(String[] args) {

		StudentInfo userInfoCollections = new StudentInfo();
		userInfoCollections.setUserName("Manoj");
		userInfoCollections.setDescription("some description");
		userInfoCollections.setCreationDate(new Date());

		Address address1 = new Address();
		address1.setCity("city1");
		address1.setState("state1");
		address1.setStreet("street1");
		address1.setPinCode("pincode1");

		Address address2 = new Address();
		address2.setCity("city_home");
		address2.setState("state_home");
		address2.setStreet("street_home");
		address2.setPinCode("pincode_home");
		
		userInfoCollections.getAddressSet().add(address1);
		userInfoCollections.getAddressSet().add(address2);

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(userInfoCollections);
		session.getTransaction().commit();
		session.close();
	}
}
```

***STUDENT_INFO***
```
mysql> select * from STUDENT_INFO;
+---------+---------------+------------------+-----------+
| USER_ID | CREATION_DATE | DESCRIPTION      | USER_NAME |
+---------+---------------+------------------+-----------+
|       1 | 16:13:58      | some description | Manoj     |
+---------+---------------+------------------+-----------+
```

***STUDENT_ADDRESS*** **(By default it would have been `STUDENT_INFO_addressSet` and foreign key column name would be `STUDENT_INFO_USER_ID` if we have not specified `@CollectionTable (name = "STUDENT_ADDRESS", joinColumns = @JoinColumn(name = "STUDENT_ID"))`)** 
```
mysql> select * from STUDENT_ADDRESS;
+------------+-----------+--------------+------------+-------------+
| STUDENT_ID | CITY_NAME | PIN_CODE     | STATE_NAME | STREET_NAME |
+------------+-----------+--------------+------------+-------------+
|          1 | city1     | pincode1     | state1     | street1     |
|          1 | city_home | pincode_home | state_home | street_home |
+------------+-----------+--------------+------------+-------------+

```

#### Draw backs of using `@ElementCollection`
* The elements of the collection have no id and Hibernate can’t address them individually. If you see the the `STUDENT_INFO_addressSet` table there is no key to identify its entries individually.
* When you add a new Object to the List or remove an existing one, Hibernate deletes all elements and inserts a new record for each item in the List
