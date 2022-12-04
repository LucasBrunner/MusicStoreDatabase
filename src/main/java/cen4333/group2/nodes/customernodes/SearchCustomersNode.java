package cen4333.group2.nodes.customernodes;

import java.sql.SQLException;

import cen4333.group2.utility.UserInput;
import cen4333.group2.Node;
import cen4333.group2.daos.sqlutilities.GetIter;
import cen4333.group2.data.Customer;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.utility.ObjectSelector;

public class SearchCustomersNode extends Node {
  public enum SearchType {
    Name,
    CustomerId,
    PersonId,
    PhoneNumber,
    Address,
    Email;

    @Override
    public String toString() {
      switch (this) {
        case Name:
          return "Name";
      
        case CustomerId:
          return "Customer ID";
    
        case PersonId:
          return "Person ID";

        case Address:
          return "Address";
          
        case Email:
          return "Email";
        
        case PhoneNumber:
          return "Phone number";
        
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
        SearchType.CustomerId,
        SearchType.PhoneNumber,
        SearchType.Address,
        SearchType.Email
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
          searchCustomer(String.format(
            "WHERE `CustomerID` = %d", 
            UserInput.getInt()
          ));
          break;

        case Address:
          System.out.print("Enter the address to search: ");
          searchCustomer(String.format(
            "WHERE `Address` LIKE \"%%%s%%\"", 
            UserInput.getString()
          ));
          break;
          
        case Email:
          System.out.print("Enter the email to search: ");
          searchCustomer(String.format(
            "WHERE `Email` LIKE \"%%%s%%\"", 
            UserInput.getString()
          ));
          break;
        
        case PhoneNumber:
          System.out.print("Enter the phone number to search: ");
          searchCustomer(String.format(
            "WHERE `PhoneNumber` LIKE \"%%%s%%\"", 
            UserInput.getString()
          ));
          break;
      
        default:
          break;
      }
    } catch (NoItemsException e) {}
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