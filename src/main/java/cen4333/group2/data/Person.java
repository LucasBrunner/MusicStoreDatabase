package cen4333.group2.data;

public class Person {
  private int id = -1;
  public PersonData data;
  
  /**
   * You probably shouldn't be creating one of these, instead use a PersonAccess method or PersonData.toPerson.
   * @param id
   */
  public Person(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public boolean hasData() {
    return data != null;
  }

  public Customer toCustomer(int id) {
    Customer output = new Customer(id);
    output.person = this;
    return output;
  }
}
