package cen4333.group2.nodes.purchasenodes;

import java.sql.Date;
import java.time.Instant;

import cen4333.group2.Node;
import cen4333.group2.data.Customer;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.Purchase;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.NothingNode;
import cen4333.group2.utility.ObjectSelector;

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
    purchase.date = new Date(Instant.now().getEpochSecond());
    while(loop()) {}
        
    new ViewPurchaseNode(new DataWithId<Purchase>(purchase), customer).runNode();
  }

  public boolean loop() {
    System.out.println("What would you like to do to the order?");
    try {
      Node selectedNode = ObjectSelector.printAndGetSelection(new Node[] {
        new ViewPurchaseNode(new DataWithId<Purchase>(purchase), customer),
        new AddProductNode(purchase),
        new AddDiscountNode(purchase),
        new NothingNode("Commit order")
      });
      if (selectedNode.getClass() == NothingNode.class) {
        return false;
      }
    } catch (NoItemsException e) {}

    return true;
  }
}
