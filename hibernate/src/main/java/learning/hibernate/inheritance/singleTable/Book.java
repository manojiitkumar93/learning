package learning.hibernate.inheritance.singleTable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "BOOK")
@DiscriminatorValue("BOOK")
public class Book extends Publication {

  private int pages;

  @Column
  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

}
