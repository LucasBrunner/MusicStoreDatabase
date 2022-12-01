package cen4333.group2.nodes.customernodes;

import cen4333.group2.Node;
import cen4333.group2.utility.Utility;
import cen4333.group2.data.Customer;
import cen4333.group2.data.PersonData;
import cen4333.group2.nodes.NothingNode;

public class ViewCustomerNode extends Node {

  private Customer customer;

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @Override
  public String getName() {
    return "View customer";
  }

  @Override
  public void runNode() {
    if (customer != null) {
      PersonData pd = customer.getPersonData();
      System.out.println(String.format(
        """
          CustomerID: %d
          Name: %s
          Phone Number: %s
          Email: %s
          Address: %s
        """, 
        customer.getId(),
        pd.firstName + " " + pd.lastName,
        pd.phoneNumber,
        pd.email,
        pd.address
      ));
        
      try {
        System.out.println("What would you like to do with this customer?");
        Node n = Utility.printAndGetSelection(new Node[] {
          new EditCustomerNode(customer),
          new NothingNode()
        });

        n.runNode();
      } catch (Exception e) {}
    } else {
      System.out.println("Error: no customer!");
    }
  }
  
}
