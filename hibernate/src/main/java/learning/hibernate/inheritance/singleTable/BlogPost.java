package learning.hibernate.inheritance.singleTable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "BLOG_POST")
@DiscriminatorValue("BLOG_POST")
public class BlogPost extends Publication {

  private String url;

  @Column
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
