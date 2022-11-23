package cen4333.group2.rework.data;

public class Customer {
  private int id = -1;
  private Person person;
  
  /**
   * You probably shouldn't be creating one of these, instead use a CustomerAccess method.
   * @param id
   */
  public Customer(int id, Person person) {
    this.id = id;
    this.person = person;
  }

  public int getId() {
    return id;
  }

  public boolean hasPerson() {
    return person != null;
  }

  public PersonData getPersonData() {
    return person.data;
  }
}
