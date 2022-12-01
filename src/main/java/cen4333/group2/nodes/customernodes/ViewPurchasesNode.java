package cen4333.group2.nodes.customernodes;

import cen4333.group2.Node;
import cen4333.group2.data.Customer;

public class ViewPurchasesNode extends Node {

  private Customer customer;

  public ViewPurchasesNode(Customer customer) {
    this.customer = customer;
  }

  @Override
  public String getName() {
    return "View purchases";
  }

  @Override
  public void runNode() {
    
  }
  
}
