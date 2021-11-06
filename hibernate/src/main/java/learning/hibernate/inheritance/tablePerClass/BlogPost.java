package learning.hibernate.inheritance.tablePerClass;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "BLOG_POST")
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
