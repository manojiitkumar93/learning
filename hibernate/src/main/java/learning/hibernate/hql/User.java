package learning.hibernate.hql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({@NamedQuery(name = "UserDetails.byId", query = "from USER_HQL where userId=:id"),
    @NamedQuery(name = "UserDetails.byName", query = "from USER_HQL where userName=:name")})
@NamedNativeQueries({@NamedNativeQuery(name = "UderDetailsNative.byId",
    query = "select * from USER_HQL where USER_ID=:id", resultClass = User.class)})
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
