package learning.hibernate.cascade.orphan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
