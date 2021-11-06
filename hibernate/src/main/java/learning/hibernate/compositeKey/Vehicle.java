package learning.hibernate.compositeKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity(name = "VEHICLE")
public class Vehicle {

  private String name;
  private Integer id;
  private User user;

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "last_name", referencedColumnName = "LAST_NAME"),
      @JoinColumn(name = "first_name", referencedColumnName = "FIRST_NAME")})
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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