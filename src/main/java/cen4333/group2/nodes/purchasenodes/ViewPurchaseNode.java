package cen4333.group2.nodes.purchasenodes;

import cen4333.group2.Main;
import cen4333.group2.Node;
import cen4333.group2.DbConnection.ConnectionType;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.utility.UserInput;
import cen4333.group2.utility.UserInput.YesNo;
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
    System.out.println("Would you like to see the purchase's full data?");
    CountOrValues countOrValues = UserInput.getYesNo() == YesNo.YES ? CountOrValues.Values : CountOrValues.Count;

    System.out.println(String.format(
      """
      \nCustomer: %s
      %s
      """, 
      customerName,
      Purchase.toString(
        purchase, 
        countOrValues, 
        Main.globalData.dbConnection.getConnectionType() == ConnectionType.MANAGER, 
        countOrValues, 
        true
      )
    ));
  }

}
