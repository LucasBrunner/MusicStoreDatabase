package cen4333.group2.nodes.customernodes;

import cen4333.group2.Node;
import cen4333.group2.data.Customer;

public class EditCustomerNode extends Node {

  private Customer customer;

  public EditCustomerNode(Customer customer) {
    this.customer = customer;
  }

  @Override
  public String getName() {
    return "Edit customer";
  }
  
  @Override
  public void runNode() {
    
  }
}
