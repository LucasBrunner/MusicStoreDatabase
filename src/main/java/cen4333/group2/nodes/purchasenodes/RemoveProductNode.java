package cen4333.group2.nodes.purchasenodes;

import cen4333.group2.Node;
import cen4333.group2.data.Purchase;
import cen4333.group2.data.PurchaseProduct;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.utility.ObjectSelector;

public class RemoveProductNode extends Node {

  private Purchase purchase;

  public RemoveProductNode(Purchase purchase) {
    this.purchase = purchase;
  }

  @Override
  public String getName() {
    return "Remove Product";
  }
  
  @Override
  public void runNode() {
    System.out.println("Which discount would you like to remove?");
    PurchaseProduct product = null;
    try {
      product = ObjectSelector.printAndGetSelection(purchase.getProducts().data);
    } catch (NoItemsException e) {}
    if (product != null) {
      System.out.println("Removing discount...");
      purchase.getProducts().data.remove(product);
    }
  }
}
