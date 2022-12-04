package cen4333.group2.nodes.personnodes;

import cen4333.group2.Node;
import cen4333.group2.data.Person;
import cen4333.group2.utility.UserInput;

public class FillPersonDataNode extends Node {

  private Person person;

  public FillPersonDataNode(Person person) {
    this.person = person;
  }

  @Override
  public String getName() {
    return "Create person";
  }

  @Override
  public void runNode() {
    if (person == null) {
      person = new Person();
    }
    System.out.print("Enter the person's first name: ");
    person.firstName = UserInput.getString();
    
    System.out.print("Enter the person's last name: ");
    person.lastName = UserInput.getString();
    
    System.out.print("Enter the person's phone number: ");
    person.phoneNumber = UserInput.getString();
    
    System.out.print("Enter the person's email: ");
    person.email = UserInput.getString();
    
    System.out.print("Enter the person's address: ");
    person.address = UserInput.getString();
  }
  
}
