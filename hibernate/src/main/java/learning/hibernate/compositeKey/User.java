package learning.hibernate.compositeKey;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "USER")
public class User {

  private UserId userId;
  private String description;
  private Date creationDate;
  private String lastName;
  private String firstName;

  @Column(name = "LAST_NAME", insertable = false, updatable = false)
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(name = "FIRST_NAME", insertable = false, updatable = false)
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
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

  @EmbeddedId
  public UserId getUserId() {
    return userId;
  }

  public void setUserId(UserId userId) {
    this.userId = userId;
  }

}
