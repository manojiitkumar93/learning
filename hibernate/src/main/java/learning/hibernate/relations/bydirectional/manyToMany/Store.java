package learning.hibernate.relations.bydirectional.manyToMany;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "STORE")
public class Store {

  private String name;
  private Integer id;
  private Set<Product> products;

  @ManyToMany
  @JoinTable(name = "STORE_PRODUCT", joinColumns = {@JoinColumn(name = "STORE_ID")},
      inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID")})
  public Set<Product> getProducts() {
    return products;
  }

  public void setProducts(Set<Product> products) {
    this.products = products;
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
