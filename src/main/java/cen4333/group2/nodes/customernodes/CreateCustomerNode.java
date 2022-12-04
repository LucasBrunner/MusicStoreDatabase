package cen4333.group2.nodes.customernodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Main;
import cen4333.group2.Node;
import cen4333.group2.sqlutilities.GetIter;
import cen4333.group2.data.Customer;
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

public class CreateCustomerNode extends Node {

  private enum CreateCustomerType {
    CREATE_NEW_CUSTOMER,
    CONVERT_EXISTING_EMPLOYEE,
    CANCEL;

    @Override
    public String toString() {
      switch (this) {
        case CONVERT_EXISTING_EMPLOYEE:
          return "Convert existing employee";
        case CREATE_NEW_CUSTOMER:
          return "Create new customer";
        default:
          return "Cancel";
      }
    }
  }

  @Override
  public String getName() {
    return "Create customer";
  }
  
  @Override
  public void runNode() {
    CreateCustomerType selectedType = CreateCustomerType.CANCEL;
    try {
      selectedType = ObjectSelector.printAndGetSelection(CreateCustomerType.values());
    } catch (NoItemsException e) {}

    Integer newPersonId = null;
    if (selectedType == CreateCustomerType.CONVERT_EXISTING_EMPLOYEE) {
      List<ObjectWithValue<String, DelayedUserInputString>> additionalOptions = new ArrayList<ObjectWithValue<String, DelayedUserInputString>>();
      additionalOptions.add(new ObjectWithValue<String, DelayedUserInputString>(
        "Employee ID", 
        new DelayedUserInputString(
          "WHERE `EmployeeID` = %d",
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
          Customer.post(employeeWithId.data.person);
          newPersonId = Main.globalData.dbConnection.getLastId();
        }
      } catch (SQLException e) {}
    } else if (selectedType == CreateCustomerType.CREATE_NEW_CUSTOMER) {
      Person personData = new Person();
      new FillPersonDataNode(personData).runNode();
      Customer.post(personData);
      newPersonId = Main.globalData.dbConnection.getLastId();
    }

    if (newPersonId != null) {
      System.out.println("Would you like to view this customer?");
      if (UserInput.getYesNo() == YesNo.YES) {
        try {
          Customer customerPrototype = new Customer();
          DataWithId<Customer> personWithId = customerPrototype.getId(newPersonId, customerPrototype);
          new ViewCustomerNode(personWithId);
        } catch (SQLException e) {
          System.out.println("Error! Could not retrive person.");
        }
      }
    }
  }

}
