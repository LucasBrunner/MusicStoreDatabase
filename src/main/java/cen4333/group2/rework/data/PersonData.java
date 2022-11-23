package cen4333.group2.rework.data;

public class PersonData {
  public String firstName;
  public String lastName;
  public String phoneNumber;
  public String email;
  public String address;

  public Person toPerson(int id) {
    Person output = new Person(id);
    output.data = this;
    return output;
  }
}
