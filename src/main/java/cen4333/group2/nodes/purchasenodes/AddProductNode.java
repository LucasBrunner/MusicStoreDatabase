package cen4333.group2.nodes.purchasenodes;

import java.sql.SQLException;

import cen4333.group2.Node;
import cen4333.group2.data.Product;
import cen4333.group2.data.Purchase;
import cen4333.group2.data.PurchaseProduct;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.utility.UserInput;

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
      product = Product.searchProductsAllMethods();
    } catch (SQLException e) {}

    if (product != null) {
      System.out.print("How many of this item would you like to add to the purchase: ");
      int amount = UserInput.getInt();
      purchase.getProductsOrNull().data.add(new PurchaseProduct(product, amount));
    }
  }
}
