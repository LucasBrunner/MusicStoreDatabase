package cen4333.group2.nodes.customernodes;

import cen4333.group2.Node;
import cen4333.group2.utility.ObjectSelector;
import cen4333.group2.data.Customer;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.Person;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.NothingNode;
import cen4333.group2.nodes.purchasenodes.CreatePurchaseNode;
import cen4333.group2.nodes.rentalnodes.CreateRentalNode;

public class ViewCustomerNode extends Node {

  private DataWithId<Customer> customerWithId;

  public ViewCustomerNode(DataWithId<Customer> customer) {
    this.customerWithId = customer;
  }

  @Override
  public String getName() {
    return "View customer";
  }

  @Override
  public void runNode() {
    if (customerWithId != null) {
      DataWithId<Person> personWithId = customerWithId.data.person;
      System.out.println(String.format(
        """
          CustomerID: %d
          Name: %s
          Phone Number: %s
          Email: %s
          Address: %s
        """, 
        customerWithId.id,
        personWithId.data.firstName + " " + personWithId.data.lastName,
        personWithId.data.phoneNumber,
        personWithId.data.email,
        personWithId.data.address
      ));
      
      while (loop(personWithId.data.fullName())) {}
    } else {
      System.out.println("Error: no customer!");
    }
  }

  private boolean loop(String name) {        
    try {
      System.out.println("What would you like to do with customer " + name +"?");
      Node n = ObjectSelector.printAndGetSelection(new Node[] {
        new EditCustomerNode(customerWithId),
        new CreatePurchaseNode(customerWithId),
        new SelectPurchasesOfCustomerNode(customerWithId),
        new DeleteCustomerNode(customerWithId.data),
        new CreateRentalNode(customerWithId),
        new SelectRentalsOfCustomer(customerWithId),
        new NothingNode()
      });

      n.runNode();
      if (n.getClass() == NothingNode.class || n.getClass() == DeleteCustomerNode.class) {
        return false;
      }
    } catch (NoItemsException e) {}
    return true;
  }
  
}
