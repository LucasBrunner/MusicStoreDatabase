package cen4333.group2.nodes.employeenodes;

import cen4333.group2.Node;
import cen4333.group2.data.Employee;
import cen4333.group2.data.Person;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.NothingNode;
import cen4333.group2.nodes.personnodes.DeletePersonDataNode;
import cen4333.group2.utility.ObjectSelector;

public class ViewEmployeeNode extends Node {

  private DataWithId<Employee> employeeWithId;

  public ViewEmployeeNode(DataWithId<Employee> employeeWithId) {
    this.employeeWithId = employeeWithId;
  }

  @Override
  public String getName() {
    return "View employee";
  }

  @Override
  public void runNode() {
    if (employeeWithId != null) {
      DataWithId<Person> personWithId = employeeWithId.data.person;
      System.out.println(String.format(
        """
        CustomerID: %d
        Name: %s
        Phone Number: %s
        Email: %s
        Address: %s
        Checking Number: %s
        """, 
        employeeWithId.id,
        personWithId.data.firstName + " " + personWithId.data.lastName,
        personWithId.data.phoneNumber,
        personWithId.data.email,
        personWithId.data.address,
        employeeWithId.data.checkingNumber
      ));
      
      while (loop(personWithId.data.fullName())) {}
    } else {
      System.out.println("Error: no customer!");
    }
  }

  private boolean loop(String name) {        
    try {
      System.out.println("What would you like to do with employee " + name +"?");
      Node n = ObjectSelector.printAndGetSelection(new Node[] {
        new EditEmployeeNode(employeeWithId),
        new DeletePersonDataNode(employeeWithId.data.person),
        new NothingNode()
      });

      n.runNode();
      if (n.getClass() == NothingNode.class || n.getClass() == DeletePersonDataNode.class) {
        return false;
      }
    } catch (NoItemsException e) {}
    return true;
  }

}
