package cen4333.group2.nodes.employeenodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Main;
import cen4333.group2.Node;
import cen4333.group2.sqlutilities.GetIter;
import cen4333.group2.data.Employee;
import cen4333.group2.data.Person;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datacontainers.ObjectWithValue;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.personnodes.FillPersonDataNode;
import cen4333.group2.utility.DelayedUserInputString;
import cen4333.group2.utility.ObjectSelector;
import cen4333.group2.utility.UserInput;
import cen4333.group2.utility.DelayedUserInputString.UserInputType;
import cen4333.group2.utility.UserInput.YesNo;

public class CreateEmployeeNode extends Node {

  private enum CreateEmployeeType {
    CREATE_NEW_EMPLOYEE,
    CONVERT_EXISTING_CUSTOMER,
    CANCEL;

    @Override
    public String toString() {
      switch (this) {
        case CONVERT_EXISTING_CUSTOMER:
          return "Convert existing customer";
        case CREATE_NEW_EMPLOYEE:
          return "Create new employee";
        default:
          return "Cancel";
      }
    }
  }

  @Override
  public String getName() {
    return "Create employee";
  }
  
  @Override
  public void runNode() {
    CreateEmployeeType selectedType = CreateEmployeeType.CANCEL;
    try {
      selectedType = ObjectSelector.printAndGetSelection(CreateEmployeeType.values());
    } catch (NoItemsException e) {}

    Integer newPersonId = null;
    if (selectedType == CreateEmployeeType.CONVERT_EXISTING_CUSTOMER) {
      List<ObjectWithValue<String, DelayedUserInputString>> additionalOptions = new ArrayList<ObjectWithValue<String, DelayedUserInputString>>();
      additionalOptions.add(new ObjectWithValue<String, DelayedUserInputString>(
        "Customer ID", 
        new DelayedUserInputString(
          "WHERE `CustomerID` = %d",
          "Enter the id to search: ",
          UserInputType.INTEGER
        )
      ));
      try {
        DataWithId<Employee> employeeWithId = new GetIter<Employee>(
          Person.personSearchString(additionalOptions), 
          new Employee(), 
          GetIter.getResultsAmount()
        ).userSelect(true, "employee", true);
        if (employeeWithId != null) {
          System.out.print("Enter the employee's checking number: ");
          Employee.post(employeeWithId.data.person, UserInput.getString());
          newPersonId = Main.globalData.dbConnection.getLastId();
        }
      } catch (SQLException e) {}
    } else if (selectedType == CreateEmployeeType.CREATE_NEW_EMPLOYEE) {
      Person personData = new Person();
      new FillPersonDataNode(personData).runNode();
      System.out.print("Enter the employee's checking number: ");
      Employee.post(personData, UserInput.getString());
      newPersonId = Main.globalData.dbConnection.getLastId();
    }

    if (newPersonId != null) {
      System.out.println("Would you like to view this employee?");
      if (UserInput.getYesNo() == YesNo.YES) {
        try {
          Employee employeePrototype = new Employee();
          DataWithId<Employee> employeeWithId = employeePrototype.getId(newPersonId, employeePrototype);
          new ViewEmployeeNode(employeeWithId).runNode();
        } catch (SQLException e) {
          System.out.println("Error! Could not retrive person.");
        }
      }
    }
  }

}
