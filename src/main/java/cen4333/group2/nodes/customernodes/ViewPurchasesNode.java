package cen4333.group2.nodes.customernodes;

import cen4333.group2.Node;
import cen4333.group2.data.Customer;
import cen4333.group2.data.DataWithId;

public class ViewPurchasesNode extends Node {

  private DataWithId<Customer> customer;

  public ViewPurchasesNode(DataWithId<Customer> customerWithId) {
    this.customer = customerWithId;
  }

  @Override
  public String getName() {
    return "View purchases";
  }

  @Override
  public void runNode() {
    
  }
  
}
