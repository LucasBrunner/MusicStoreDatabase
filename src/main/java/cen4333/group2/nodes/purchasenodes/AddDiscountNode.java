package cen4333.group2.nodes.purchasenodes;

import java.sql.SQLException;

import cen4333.group2.Main;
import cen4333.group2.Node;
import cen4333.group2.data.Discount;
import cen4333.group2.data.Purchase;
import cen4333.group2.data.PurchaseDiscount;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.utility.UserInput;
import cen4333.group2.utility.UserInput.YesNo;

public class AddDiscountNode extends Node {

  private Purchase purchase;

  public AddDiscountNode(Purchase purchase) {
    this.purchase = purchase;
  }{
  }

  @Override
  public String getName() {
    return "Add discount";
  }

  @Override
  public void runNode() {
    DataWithId<Discount> discount = null;
    try {
      discount = Discount.searchDiscountsAllMethods(Main.globalData.dbConnection.getConnectionType().canViewUnavailableDiscounts());
    } catch (SQLException e) {}

    if (discount != null) {
      System.out.println(discount.data.toString());
      System.out.println("Would you like to add this discount to the purchase?");
      if (UserInput.getYesNo() == YesNo.YES) {        
        purchase.getDiscounts().data.add(new PurchaseDiscount(discount));
      }
    }
  }

}
