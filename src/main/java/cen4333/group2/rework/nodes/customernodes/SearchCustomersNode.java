package cen4333.group2.rework.nodes.customernodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.UserInput;
import cen4333.group2.rework.Node;
import cen4333.group2.rework.Utility;
import cen4333.group2.rework.Utility.SearchType;
import cen4333.group2.rework.daos.CustomerDao;
import cen4333.group2.rework.data.Customer;
import cen4333.group2.rework.data.PersonData;
import cen4333.group2.rework.errors.NoItemsException;

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
    List<String> selections = new ArrayList<String>();
    while (true) {
      try {
        System.out.printf("\nPrinting customers %d to %d. Select one to view it:\n", offset, offset + csi.amount);
        boolean moredata = csi.getCustomersFromDb(offset, csi.amount, customers);

        selections.clear();
        for (Customer customer : customers) {
          PersonData pd = customer.getPersonData();
          selections.add("ID: " + customer.getId() + ", Name: " + pd.firstName + " " + pd.lastName);
        }

        if (moredata) {
          selections.add("Next page");
        }
        if (offset > 0) {
          selections.add("Previous page");
        }
        selections.add("Cancel");

        String selection = Utility.printAndGetSelection(selections);

        if (selection == "Next page") {
          offset += csi.amount;
        } else if (selection == "Previous page") {
          offset -= csi.amount;
        } else if (selection == "Cancel") {
          return;
        } else {
          // This code gets the id number from a string with the format of "ID: 2, Name: Chi Littel".
          boolean foundNum = false;
          String num = "";
          for (char c : selection.toCharArray()) {
            if (Character.isDigit(c)) {
              foundNum = true;
              num = num + c;
            } else if (foundNum) {
              break;
            }
          }
          int selectedId = Integer.parseInt(num);
  
          int i = 0; 
          while (i < customers.size()) {
            if (customers.get(i).getId() == selectedId) {
              break;
            }
            i += 1;
          }

          ViewCustomerNode vcn = new ViewCustomerNode();
          vcn.setCustomer(customers.get(i));
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