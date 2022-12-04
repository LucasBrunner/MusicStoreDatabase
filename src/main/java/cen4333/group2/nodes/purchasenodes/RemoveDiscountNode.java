package cen4333.group2.nodes.purchasenodes;

import cen4333.group2.Node;
import cen4333.group2.data.Purchase;
import cen4333.group2.data.PurchaseDiscount;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.utility.ObjectSelector;

public class RemoveDiscountNode extends Node {

  private Purchase purchase;

  public RemoveDiscountNode(Purchase purchase) {
    this.purchase = purchase;
  }

  @Override
  public String getName() {
    return "Remove Discount";
  }
  
  @Override
  public void runNode() {
    System.out.println("Which discount would you like to remove?");
    PurchaseDiscount discount = null;
    try {
      discount = ObjectSelector.printAndGetSelection(purchase.getDiscounts().data);
    } catch (NoItemsException e) {}
    if (discount != null) {
      System.out.println("Removing discount...");
      purchase.getDiscounts().data.remove(discount);
    }
  }
}
