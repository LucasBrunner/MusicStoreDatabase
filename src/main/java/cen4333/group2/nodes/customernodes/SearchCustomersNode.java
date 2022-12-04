package cen4333.group2.nodes.customernodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.utility.DelayedUserInputString.UserInputType;
import cen4333.group2.Node;
import cen4333.group2.sqlutilities.GetIter;
import cen4333.group2.data.Customer;
import cen4333.group2.data.Person;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datacontainers.ObjectWithValue;
import cen4333.group2.utility.DelayedUserInputString;

public class SearchCustomersNode extends Node {

  @Override
  public String getName() {
    return "Search customers";
  }

  @Override
  public void runNode() {
    System.out.println("How would you like to search by?");
    List<ObjectWithValue<String, DelayedUserInputString>> additionalOptions = new ArrayList<ObjectWithValue<String, DelayedUserInputString>>();
    additionalOptions.add(new ObjectWithValue<String, DelayedUserInputString>(
      "Customer ID", 
      new DelayedUserInputString(
        "WHERE `CustomerID` = %d", 
        "Enter the id to search: ",
        UserInputType.INTEGER
      )
    ));
    searchCustomer(Person.personSearchString(additionalOptions));
  }

  private void searchCustomer(String where) {
    try {
      DataWithId<Customer> selectedCustomer = new GetIter<Customer>(
        where, 
        new Customer(), 
        GetIter.getResultsAmount()
      ).userSelect(true, "customer", true);
      if (selectedCustomer != null) {
        new ViewCustomerNode(selectedCustomer).runNode();
      } else {

      }
    } catch (SQLException e) {
      System.out.println("There was a database error!");
      e.printStackTrace();
    }
  }
}