package learning.hibernate.inheritance.mapperSuperClass;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BlogPost extends Publication {

  private String url;

  @Column(nullable = false, updatable = true)
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
