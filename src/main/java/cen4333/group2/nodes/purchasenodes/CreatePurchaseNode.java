package cen4333.group2.nodes.purchasenodes;

import cen4333.group2.Node;
import cen4333.group2.data.Customer;

public class CreatePurchaseNode extends Node {

  Customer customer;

  public CreatePurchaseNode(Customer customer) {
    this.customer = customer;
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
    System.out.println("What would you like to add to the order?");
    

    return true;
  }
  
}
