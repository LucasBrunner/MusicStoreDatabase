package cen4333.group2.nodes.purchasenodes;

import cen4333.group2.Main;
import cen4333.group2.Node;
import cen4333.group2.DbConnection.ConnectionType;
import cen4333.group2.data.DataWithId;
import cen4333.group2.data.Purchase;
import cen4333.group2.data.Purchase.CountOrValues;

public class ViewPurchaseNode extends Node {

  private DataWithId<Purchase> purchase;
  private String customerName;

  public ViewPurchaseNode(DataWithId<Purchase> purchase, String customerName) {
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
      %s
      """, 
      customerName,
      Purchase.toString(
        purchase, 
        CountOrValues.Count, 
        Main.globalData.dbConnection.getConnectionType() != ConnectionType.MANAGER, 
        CountOrValues.Count, 
        true
      )
    ));
  }

}
