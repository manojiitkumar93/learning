package learning.hibernate.relations.bydirectional.manyToMany;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "PRODUCT")
public class Product {

  private Integer userId;
  private String name;
  private String description;
  private Date creationDate;
  private Set<Store> stores;


  @ManyToMany(mappedBy = "products")
  public Set<Store> getStores() {
    return stores;
  }

  public void setStores(Set<Store> stores) {
    this.stores = stores;
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
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Column(name = "NAME")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
