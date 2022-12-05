package cen4333.group2.nodes.employeenodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Node;
import cen4333.group2.sqlutilities.GetIter;
import cen4333.group2.data.Employee;
import cen4333.group2.data.Person;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datacontainers.ObjectWithValue;
import cen4333.group2.utility.DelayedUserInputString;
import cen4333.group2.utility.DelayedUserInputString.UserInputType;

public class SearchEmployeesNode extends Node {

  @Override
  public String getName() {
    return "Search employees";
  }
  
  @Override
  public void runNode() {
    System.out.println("What would you like to search by?");
    List<ObjectWithValue<String, DelayedUserInputString>> additionalOptions = new ArrayList<ObjectWithValue<String, DelayedUserInputString>>();
    additionalOptions.add(new ObjectWithValue<String, DelayedUserInputString>(
      "Employee ID", 
      new DelayedUserInputString(
        "WHERE `EmployeeID` = %d", 
        "Enter the id to search: ",
        UserInputType.INTEGER
      )
    ));
    searchCustomer(Person.personSearchString(additionalOptions));
  }

  private void searchCustomer(String where) {
    try {
      DataWithId<Employee> selectedEmployee = new GetIter<Employee>(
        where, 
        new Employee(), 
        GetIter.getResultsAmount()
      ).userSelect(true, "customer", true);
      if (selectedEmployee != null) {
        new ViewEmployeeNode(selectedEmployee).runNode();
      } else {

      }
    } catch (SQLException e) {
      System.out.println("There was a database error!");
      e.printStackTrace();
    }
  }
}
