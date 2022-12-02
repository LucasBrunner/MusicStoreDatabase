package cen4333.group2.nodes.customernodes;

import java.sql.SQLException;

import cen4333.group2.utility.UserInput;
import cen4333.group2.Node;
import cen4333.group2.daos.sqlutilities.SelectFromWhereIter;
import cen4333.group2.data.Customer;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.utility.ObjectSelector;

public class SearchCustomersNode extends Node {
  public enum SearchType {
    Name,
    CustomerId,
    PersonId;

    @Override
    public String toString() {
      switch (this) {
        case Name:
          return "Name";
      
        case CustomerId:
          return "Customer ID";
    
        case PersonId:
          return "Person ID";
    
        default:
          return "";
      }
    }
  }

  @Override
  public String getName() {
    return "Search customers";
  }

  @Override
  public void runNode() {
    System.out.println("How would you like to search by?");
    try {
      SearchType searchType = ObjectSelector.printAndGetSelection(new SearchType[] {
        SearchType.Name,
        SearchType.CustomerId
      });

      switch (searchType) {
        case Name:
          System.out.print("Enter the name to search: ");
          searchCustomer(String.format(
            "WHERE CONCAT(`FirstName`, \" \", `LastName`) LIKE \"%%%s%%\"", 
            UserInput.getString()
          ));
          break;

        case CustomerId:
          System.out.print("Enter the ID to search: ");
          int customerId = UserInput.getInt();
          searchCustomer(String.format(
            "WHERE `CustomerID` = %d", 
            customerId
          ));
          break;
      
        default:
          break;
      }
    } catch (NoItemsException e) {}
  }

  private void searchCustomer(String where) {
    try {
      DataWithId<Customer> selectedCustomer = new SelectFromWhereIter<Customer>(
        where, 
        new Customer(), 
        SelectFromWhereIter.getResultsAmount()
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