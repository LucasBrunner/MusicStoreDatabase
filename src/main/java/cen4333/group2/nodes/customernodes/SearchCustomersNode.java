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
import cen4333.group2.data.Customer;
import cen4333.group2.data.PersonData;
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
    displaySearch(new CustomerSearchInterface(getResultsAmount()) {
      @Override
      public boolean getCustomersFromDb(int offset, int amount, List<Customer> customers) throws SQLException {
        return CustomerDao.searchCustomersByName(name, amount, offset, customers);
      }            
    });
  }

  private void searchById(int customerId) {
    displaySearch(new CustomerSearchInterface(1) {
      @Override
      public boolean getCustomersFromDb(int offset, int amount, List<Customer> customers) throws SQLException {
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

  private void displaySearch(CustomerSearchInterface csi) {
    int offset = 0;
    List<Customer> customers = new ArrayList<Customer>();
    List<ObjectWithValue<String, Integer>> selections = new ArrayList<ObjectWithValue<String, Integer>>();
    while (true) {
      try {
        System.out.printf("\nPrinting customers %d to %d. Select one to view it:\n", offset, offset + csi.amount);
        boolean moredata = csi.getCustomersFromDb(offset, csi.amount, customers);

        selections.clear();
        for (int i = 0; i < customers.size(); i++) {
          Customer customer = customers.get(i);
          PersonData pd = customer.getPersonData();
          selections.add(new ObjectWithValue<String, Integer>(
            "ID: " + customer.getId() + ", Name: " + pd.firstName + " " + pd.lastName, 
            selections.size()
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

        ObjectWithValue<String, Integer> selection = Utility.printAndGetSelection(selections);

        if (selection.value == -1) {
          offset += csi.amount;
        } else if (selection.value == -2) {
          offset -= csi.amount;
        } else if (selection.value == -3) {
          return;
        } else {
          ViewCustomerNode vcn = new ViewCustomerNode();
          vcn.setCustomer(customers.get(selection.value));
          vcn.runNode();
          break;
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

  public abstract boolean getCustomersFromDb(int offset, int amount, List<Customer> customers) throws SQLException;
}