package cen4333.group2.data;

public class Customer {
  private int id = -1;
  public Person person;
  
  /**
   * You probably shouldn't be creating one of these, instead use a CustomerAccess method.
   * @param id
   */
  public Customer(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public boolean hasPerson() {
    return person != null;
  }
}
