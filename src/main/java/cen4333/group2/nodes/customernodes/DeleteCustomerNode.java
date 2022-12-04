package cen4333.group2.nodes.customernodes;

import java.sql.SQLException;
import java.util.ArrayList;

import cen4333.group2.Main;
import cen4333.group2.Node;
import cen4333.group2.data.Customer;
import cen4333.group2.data.Employee;
import cen4333.group2.utility.UserInput;
import cen4333.group2.utility.UserInput.YesNo;

public class DeleteCustomerNode extends Node {

  private Customer customer;

  public DeleteCustomerNode(Customer customer) {
    this.customer = customer;
  }

  @Override
  public String getName() {
    return "Delete customer data";
  }

  @Override
  public void runNode() {
    System.out.println("Are you sure you want to delete this person's data?");
    if (UserInput.getYesNo() == YesNo.YES) {
      try {
        if (!Employee.isPersonEmployee(customer.person) || Main.globalData.dbConnection.getConnectionType().canDeleteEmployeeData()) {
          customer.person.data.delete(customer.person, new ArrayList<String>());
        } else {
          System.out.println("You do not have the authorization to delete the data for customers who are also employees.");
        }
      } catch (SQLException e) {
        System.out.println("Error! could not delete the customer's data.");
      }
    }
  }
  
}
