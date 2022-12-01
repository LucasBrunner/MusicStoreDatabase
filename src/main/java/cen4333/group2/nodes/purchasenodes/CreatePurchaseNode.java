package cen4333.group2.nodes.purchasenodes;

import cen4333.group2.Node;
import cen4333.group2.data.Customer;
import cen4333.group2.data.DataWithId;
import cen4333.group2.data.Purchase;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.NothingNode;
import cen4333.group2.utility.Utility;

public class CreatePurchaseNode extends Node {

  DataWithId<Customer> customer;
  Purchase purchase = new Purchase();

  public CreatePurchaseNode(DataWithId<Customer> customerWithId) {
    this.customer = customerWithId;
  }

  @Override
  public String getName() {
    return "Create purchase";
  }

  @Override
  public void runNode() {
    while(loop()) {}
  }

  public boolean loop() {
    System.out.println("What would you like to do to the order?");
    try {
      Utility.printAndGetSelection(new Node[] {
        new ViewPurchaseNode(purchase, customer.data.person.data.fullName()),
        new AddProductNode(purchase),
        new AddDiscountNode(purchase),
        new NothingNode("Commit order")
      });
    } catch (NoItemsException e) {}

    return true;
  }
  
}
