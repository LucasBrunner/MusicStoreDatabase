package cen4333.group2.nodes.purchasenodes;

import java.sql.SQLException;

import cen4333.group2.Main;
import cen4333.group2.Node;
import cen4333.group2.data.Discount;
import cen4333.group2.data.Purchase;
import cen4333.group2.data.PurchaseDiscount;
import cen4333.group2.data.datacontainers.DataWithId;

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
      purchase.getDiscountsOrNull().data.add(new PurchaseDiscount(discount));
    }
  }

}
