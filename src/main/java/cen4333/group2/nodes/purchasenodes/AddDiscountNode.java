package cen4333.group2.nodes.purchasenodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
      List<String> whereClauses = new ArrayList<String>();
      List<Integer> ids = PurchaseDiscount.getIds(purchase.getDiscounts().data);
      if (ids.size() > 0) {
        StringBuilder whereClause = new StringBuilder("`discount`.`DiscountID` NOT IN (");
        for (int i = 0; i < ids.size() - 1; i++) {
          whereClause.append(ids.get(i));
          whereClause.append(",");
        }
        whereClause.append(ids.get(ids.size() - 1));
        whereClause.append(")");
        whereClauses.add(whereClause.toString());
      }
      discount = Discount.searchDiscountsAllMethods(Main.globalData.dbConnection.getConnectionType().canViewUnavailableDiscounts(), whereClauses);
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
