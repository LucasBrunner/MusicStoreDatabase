package cen4333.group2.nodes.purchasenodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Node;
import cen4333.group2.data.Product;
import cen4333.group2.data.Purchase;
import cen4333.group2.data.PurchaseProduct;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.utility.UserInput;
import cen4333.group2.utility.UserInput.YesNo;

public class AddProductNode extends Node {

  private Purchase purchase;

  public AddProductNode(Purchase purchase) {
    this.purchase = purchase;
  }

  @Override
  public String getName() {
    return "Add product";
  }

  @Override
  public void runNode() {
    DataWithId<Product> product = null;
    try {
      List<String> whereClauses = new ArrayList<String>();
      List<Integer> ids = PurchaseProduct.getIds(purchase.getProducts().data);
      if (ids.size() > 0) {
        StringBuilder whereClause = new StringBuilder("`product`.`ProductID` NOT IN (");
        for (int i = 0; i < ids.size() - 1; i++) {
          whereClause.append(ids.get(i));
          whereClause.append(",");
        }
        whereClause.append(ids.get(ids.size() - 1));
        whereClause.append(")");
        whereClauses.add(whereClause.toString());
      }
      product = Product.searchProductsAllMethods(whereClauses);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (product != null) {
      System.out.println(product.data.toString());
      System.out.println("Would you like to add this item to the purchase?");
      if (UserInput.getYesNo() == YesNo.YES) {
        System.out.print("How many of this item would you like to add to the purchase: ");
        int amount = UserInput.getInt();
        
        purchase.getProducts().data.add(new PurchaseProduct(product, amount));
      }
    }
  }
}
