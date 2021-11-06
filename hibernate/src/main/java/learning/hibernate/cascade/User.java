package learning.hibernate.cascade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "USER_CASCADE")
public class User {

  private Integer userId;
  private String userName;
  private String description;
  private Date creationDate;

  private Collection<Vehicle> vehicles = new ArrayList();

  @OneToMany(cascade = CascadeType.ALL)
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
