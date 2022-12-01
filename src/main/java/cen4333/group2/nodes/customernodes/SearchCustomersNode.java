package cen4333.group2.nodes.customernodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.utility.ObjectWithValue;
import cen4333.group2.utility.UserInput;
import cen4333.group2.Node;
import cen4333.group2.utility.Utility;
import cen4333.group2.utility.Utility.SearchType;
import cen4333.group2.daos.CustomerDao;
import cen4333.group2.daos.sqlutilities.SelectFromWhereIter;
import cen4333.group2.data.Customer;
import cen4333.group2.data.DataWithId;
import cen4333.group2.data.Person;
import cen4333.group2.errors.NoItemsException;

public class SearchCustomersNode extends Node {

  @Override
  public String getName() {
    return "Search customers";
  }

  @Override
  public void runNode() {
    System.out.println("How would you like to search by?");
    try {
      SearchType searchType = Utility.printAndGetSelection(new SearchType[] {
        SearchType.Name,
        SearchType.CustomerId
      });

      switch (searchType) {
        case Name:
          System.out.print("Enter the name to search: ");
          String name = UserInput.getString();
          searchByName(name);
          break;

        case CustomerId:
          System.out.print("Enter the ID to search: ");
          int customerId = UserInput.getInt();
          searchById(customerId);
          break;
      
        default:
          break;
      }
    } catch (NoItemsException e) {}
  }

  private void searchByName(String name) {
    try {
      displaySearch(new SelectFromWhereIter<Customer>(
        String.format("WHERE CONCAT(`FirstName`, \" \", `LastName`) LIKE \"%%%s%%\"", name), 
        new Customer(), 
        getResultsAmount()
      ));
    } catch (SQLException e) {
      System.out.println("There was a database error!");
      e.printStackTrace();
    }
    // displaySearch(new CustomerSearchInterface(getResultsAmount()) {
    //   @Override
    //   public boolean getCustomersFromDb(int offset, int amount, List<DataWithId<Customer>> customers) throws SQLException {
    //     return CustomerDao.searchCustomersByName(name, amount, offset, customers);
    //   }            
    // });
  }

  private void searchById(int customerId) {
    displaySearch(new CustomerSearchInterface(1) {
      @Override
      public boolean getCustomersFromDb(int offset, int amount, List<DataWithId<Customer>> customers) throws SQLException {
        return CustomerDao.searchCustomersById(customerId, amount, offset, customers);
      }            
    });
  }

  private int getResultsAmount() {
    int amount = 0;
    System.out.print("How many results per page (1-20): ");
    while (true) {
      amount = UserInput.getInt();
      if (amount < 1) {
        System.out.print("Must be more than 0: ");
      } else if (amount > 20) {
        System.err.print("Cannot be greater than 20: ");
      } else {
        break;
      }
    }
    return amount;
  }

  private void displaySearch(SelectFromWhereIter<Customer> searchIter) throws ClassCastException, SQLException {
    List<DataWithId<Customer>> customers = new ArrayList<DataWithId<Customer>>();
    List<ObjectWithValue<String, Integer>> selections = new ArrayList<ObjectWithValue<String, Integer>>();
    while (true) {
      System.out.printf(
        "\nPrinting customers %d to %d. Select one to view it:\n", 
        searchIter.getOffset() + 1, 
        searchIter.getOffset() + searchIter.getStepSize()
      );
    
      customers = searchIter.getPage();
      selections.clear();
      for (int i = 0; i < customers.size(); i++) {
        DataWithId<Customer> customer = customers.get(i);
        DataWithId<Person> pd = customer.data.person;
        selections.add(new ObjectWithValue<String, Integer>(
          "ID: " + customer.id + ", Name: " + pd.data.firstName + " " + pd.data.lastName, 
          selections.size() + 1
          ));
      }

      if (searchIter.hasNextPage()) {
        selections.add(new ObjectWithValue<String, Integer>("Next page", -1));
      }
      if (searchIter.hasPreviousPage()) {
        selections.add(new ObjectWithValue<String, Integer>("Previous page", -2));
      }
      selections.add(new ObjectWithValue<String, Integer>("Cancel", -3));

      Integer selection = null;
      try {
        selection = Utility.printAndGetSelection(selections).value;
      } catch (NoItemsException e) {}

      if (selection == -1) {
        searchIter.nextPage();
      } else if (selection == -2) {
        searchIter.previousPage();
      } else if (selection == -3) {
        return;
      } else if (selection >= 1 && selection <= customers.size()) {
        ViewCustomerNode vcn = new ViewCustomerNode();
        vcn.setCustomer(customers.get(selection - 1));
        vcn.runNode();
        break;
      } else {
        System.out.println("Invalid state! Proceeding to next valid state.");
      }
    }
  }

  private void displaySearch(CustomerSearchInterface csi) {
    int offset = 0;
    List<DataWithId<Customer>> customers = new ArrayList<DataWithId<Customer>>();
    List<ObjectWithValue<String, Integer>> selections = new ArrayList<ObjectWithValue<String, Integer>>();
    while (true) {
      try {
        System.out.printf("\nPrinting customers %d to %d. Select one to view it:\n", offset, offset + csi.amount);
        boolean moredata = csi.getCustomersFromDb(offset, csi.amount, customers);

        selections.clear();
        for (int i = 0; i < customers.size(); i++) {
          DataWithId<Customer> customer = customers.get(i);
          DataWithId<Person> pd = customer.data.person;
          selections.add(new ObjectWithValue<String, Integer>(
            "ID: " + customer.id + ", Name: " + pd.data.firstName + " " + pd.data.lastName, 
            selections.size() + 1
            ));
        }

        // The IDs should always be positive so unless something went very wrong this is safe.
        if (moredata) {
          selections.add(new ObjectWithValue<String, Integer>("Next page", -1));
        }
        if (offset > 0) {
          selections.add(new ObjectWithValue<String, Integer>("Previous page", -2));
        }
        selections.add(new ObjectWithValue<String, Integer>("Cancel", -3));

        Integer selection = Utility.printAndGetSelection(selections).value;

        if (selection == -1) {
          offset += csi.amount;
        } else if (selection == -2) {
          offset -= csi.amount;
        } else if (selection == -3) {
          return;
        } else if (selection >= 1 && selection <= customers.size()) {
          ViewCustomerNode vcn = new ViewCustomerNode();
          vcn.setCustomer(customers.get(selection - 1));
          vcn.runNode();
          break;
        } else {
          System.out.println("Invalid state! Proceeding to next valid state.");
        }

      } catch (SQLException e) {
        e.printStackTrace();
      } catch (NoItemsException e) {

      }

    }
  }
}

abstract class CustomerSearchInterface {
  public int amount;

  public CustomerSearchInterface(int amount) {
    this.amount = amount;
  }

  public abstract boolean getCustomersFromDb(int offset, int amount, List<DataWithId<Customer>> customers) throws SQLException;
}