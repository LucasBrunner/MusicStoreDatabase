package cen4333.group2.nodes.purchasenodes;

import cen4333.group2.Main;
import cen4333.group2.Node;
import cen4333.group2.data.Purchase;

public class ViewPurchaseNode extends Node {

  private Purchase purchase;
  private String customerName;

  public ViewPurchaseNode(Purchase purchase, String customerName) {
    this.purchase = purchase;
    this.customerName = customerName;
  }

  @Override
  public String getName() {
    return "View Purchase";
  }

  @Override
  public void runNode() {
    System.out.println(String.format(
      """
        Customer: %s
        Date of purchase: %s
        Products: %s
        Discounts: %s
      """, 
      customerName,
      purchase.date.toLocalDate().toString(),
      purchase.productsAsString(Main.globalData.dbConnection.getConnectionType().showWholesalePrices()),
      purchase.discountsAsString(false)
    ));
  }

}
