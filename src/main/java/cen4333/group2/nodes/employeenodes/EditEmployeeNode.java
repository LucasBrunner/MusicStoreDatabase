package cen4333.group2.nodes.employeenodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Node;
import cen4333.group2.data.Employee;
import cen4333.group2.data.Person;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datacontainers.ObjectWithValue;
import cen4333.group2.utility.ObjectSelector;
import cen4333.group2.utility.UserInput;

public class EditEmployeeNode extends Node {
  DataWithId<Employee> employeeWithId;

  public EditEmployeeNode(DataWithId<Employee> employeeWithId) {
    this.employeeWithId = employeeWithId;
  }

  @Override
  public String getName() {
    return "Edit employee";
  }
  
  @Override
  public void runNode() {
    while (loop()) {}
  }

  public boolean loop() {
    System.out.println("Which field would you like to edit?");

    List<ObjectWithValue<String, Integer>> fields = new ArrayList<ObjectWithValue<String, Integer>>();
    Person p = employeeWithId.data.person.data;
    fields.add(ObjectWithValue.stringWithInt("First name: " + p.firstName, fields.size() + 1));
    fields.add(ObjectWithValue.stringWithInt("Last name: " + p.lastName, fields.size() + 1));
    fields.add(ObjectWithValue.stringWithInt("Phone number: " + p.phoneNumber, fields.size() + 1));
    fields.add(ObjectWithValue.stringWithInt("Email: " + p.email, fields.size() + 1));
    fields.add(ObjectWithValue.stringWithInt("Address: " + p.address, fields.size() + 1));
    fields.add(ObjectWithValue.stringWithInt("Checking number: " + employeeWithId.data.checkingNumber, fields.size() + 1));

    fields.add(ObjectWithValue.stringWithInt("Save and exit", -1));
    fields.add(ObjectWithValue.stringWithInt("cancel", -2));
    Integer selection = null;

    try {
      selection = ObjectSelector.printAndGetSelection(fields).value;
    } catch (Exception e) {}
    
    if (selection == -1) {
      System.out.println("Finished editing customer.");
      try {
        employeeWithId.data.put(employeeWithId);
        return false;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else if (selection == -2) {
      System.out.println("Canceled editing customer.");
    } else if (selection >= 1 && selection <= 6) {
      switch (selection) {
        case 1:
          System.out.print("Enter the Employee's new first name: ");
          p.firstName = UserInput.getString();
          break;
        case 2:
          System.out.print("Enter the Employee's new last name: ");
          p.lastName = UserInput.getString();
          break;
        case 3:
          System.out.print("Enter the Employee's new phone number: ");
          p.phoneNumber = UserInput.getString();
          break;
        case 4:
          System.out.print("Enter the Employee's new email: ");
          p.email = UserInput.getString();
          break;
        case 5:
          System.out.print("Enter the Employee's new address: ");
          p.address = UserInput.getString();
          break;
        case 6:
          System.out.print("Enter the Employee's new checking number: ");
          employeeWithId.data.checkingNumber = UserInput.getString();
          break;
      
        default:
          System.out.println("Invalid state! Proceeding to next valid state.");
          break;
      }
    } else {
      System.out.println("Invalid state! Proceeding to next valid state.");
    }
    return true;
  }

}
