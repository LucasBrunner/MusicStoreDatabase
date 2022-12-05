package cen4333.group2.nodes.rentalnodes;

import cen4333.group2.Node;
import cen4333.group2.data.Customer;
import cen4333.group2.data.datacontainers.DataWithId;

public class CreateRentalNode extends Node {
  private DataWithId<Customer> customerWithId;

  public CreateRentalNode(DataWithId<Customer> customerWithId) {
    this.customerWithId = customerWithId;
  }

  @Override
  public String getName() {
    return "Create rental";
  }

  @Override
  public void runNode() {
    
  }
  
}
