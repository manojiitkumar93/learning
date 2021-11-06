package learning.hibernate.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "BOOK")
public class Book extends Publication {

  private int pages;

  @Column(nullable = false, updatable = true)
  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

}
