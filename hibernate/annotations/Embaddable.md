```
@Embeddable
@Embedded
```
**To understand about `@Embeddable` and `@Embedded` annotation we need to have an idea about types of objects Hibernate has..**
* Value Object
* Entities

***Value Objects*** are the objects which can not stand alone.
Take `Address`, for example. If you say address, people will ask whose address is this
So it can not stand alone.<br>
***Entity Objects*** are those who can stand alone like `User` and `Student` <br>
So in case of value objects preferred way is to Embed them into an entity object.<br>
<br>
**Why do we need to create seperate classes for Value Objects?**<br>
To answer why we are creating two different classes: 
first of all, it's a OOPS concept that you should have loose 
coupling and high cohesion among classes. That means you should create classes for 
specialized purpose only. For example, your Student class should only have the info 
related to Student.<br>
<br>
Second point is that by creating different classes you promote re-usability.
* When we define the value object for the entity class we use `@Embeddable`.
* When we use value type object in entity class we use `@Embedded`

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
@Entity(name = "USER_INFO")
public class UserInfo {

	private Integer userId;
	private String userName;
	private String description;
	private Date creationDate;

	private Address officeAddress;
	private Address homeAddress;

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

	public Address getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(Address officeAddress) {
		this.officeAddress = officeAddress;
	}

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(name = "HOME_STREET")),
			@AttributeOverride(name = "city", column = @Column(name = "HOME_CITY")),
			@AttributeOverride(name = "state", column = @Column(name = "HOME_STATE")),
			@AttributeOverride(name = "pinCode", column = @Column(name = "HOME_PIN_CODE")) })
	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

}
```
**`@AttributeOverrides` provides a way to override the column names of the `@Embeddable` (Address.java) when using the same Value Object multiple times in the same Entity Object**

```
public class HibernateTest {
	public static void main(String[] args) {

		UserInfo userInfo = new UserInfo();
		userInfo.setUserName("Manoj");
		userInfo.setDescription("some description");
		userInfo.setCreationDate(new Date());

		Address officeAddress = new Address();
		officeAddress.setCity("city1");
		officeAddress.setState("state1");
		officeAddress.setStreet("street1");
		officeAddress.setPinCode("pincode1");
		userInfo.setOfficeAddress(officeAddress);
		
		Address homeAddress = new Address();
		homeAddress.setCity("city_home");
		homeAddress.setState("state_home");
		homeAddress.setStreet("street_home");
		homeAddress.setPinCode("pincode_home");
		userInfo.setHomeAddress(homeAddress);

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(userInfo);
		session.getTransaction().commit();
		session.close();
	}
}
```

```
mysql> select * from USER_INFO\G
*************************** 1. row ***************************
      USER_ID: 1
CREATION_DATE: 14:22:21
  DESCRIPTION: some description
    HOME_CITY: city_home
HOME_PIN_CODE: pincode_home
   HOME_STATE: state_home
  HOME_STREET: street_home
    CITY_NAME: city1
     PIN_CODE: pincode1
   STATE_NAME: state1
  STREET_NAME: street1
    USER_NAME: Manoj
1 row in set (0.00 sec)
```

