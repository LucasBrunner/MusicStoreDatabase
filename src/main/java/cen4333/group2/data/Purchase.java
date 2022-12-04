package cen4333.group2.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cen4333.group2.daos.sqlutilities.Post;
import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.Get;
import cen4333.group2.data.datacontainers.DataList;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class Purchase implements QueryResult, Get<Purchase>, CreateInstance, Duplicate, Post<Integer> {
  public enum CountOrValues {
    Count,
    Values
  }

  public Date date;
  private DataList<PurchaseProduct> products = new DataList<PurchaseProduct>();
  private DataList<PurchaseDiscount> discounts = new DataList<PurchaseDiscount>();

  public DataList<PurchaseProduct> getProducts() {
    return products;
  }

  public DataList<PurchaseDiscount> getDiscounts() {
    return discounts;
  }

  public String productsAsString(boolean showWholesalePrice, int purchaseId) throws SQLException {
    StringBuilder output = new StringBuilder();
    List<PurchaseProduct> products = this.products.data;
    if (products.size() > 0) {
      for (int i = 0; i < products.size(); i++) {
        String product = products.get(i).toString(showWholesalePrice);
        product = product.replace("\n", "\n  ");
        output.append("Product " + (i + 1) + ":\n  ");
        output.append(product);
        output.append("\n");
      }
    } else {
      output.append("No products");
    }
    return output.toString();
  }

  public String discountsAsString(boolean showDates, int purchaseId) throws SQLException {
    StringBuilder output = new StringBuilder();
    List<PurchaseDiscount> discounts = this.discounts.data;
    if (discounts.size() > 0) {
      for (int i = 0; i < discounts.size(); i++) {
        String discount = discounts.get(i).toString(showDates);
        discount = discount.replace("\n", "\n  ");
        output.append("Discount " + (i + 1) + ":\n  ");
        output.append(discount);
        output.append("\n");
      }
    } else {
      output.append("No discounts");
    }
    return output.toString();
  }

  public int productCount(int purchaseId) throws SQLException {
    return products.data.size();
  }

  public int discountCount(int purchaseId) throws SQLException {
    return discounts.data.size();
  }

  public static String toString(DataWithId<Purchase> purchase, CountOrValues showProducts, boolean showWholesalePrices, CountOrValues showDiscounts, boolean showDiscountDates) {
    StringBuilder output = new StringBuilder();
    output.append("Date of purchase: " + purchase.data.date.toString());

    // products
    boolean productsAdded = false;
    if (showProducts == CountOrValues.Values) {
      try {
        output.append("\n" + purchase.data.productsAsString(showWholesalePrices, purchase.id));
        productsAdded = true;
      } catch (SQLException e) {
        productsAdded = false;
      }
    } else if (purchase.data.products != null || productsAdded == false) {
      try {
        output.append("\nProduct count: " + purchase.data.productCount(purchase.id));
      } catch (Exception e) {
        output.append("\nProduct count: {Error displaying count}");
      }
    }

    // discounts
    boolean discountsAdded = false;
    if (showDiscounts == CountOrValues.Values) {
      try {
        output.append("\n" + purchase.data.discountsAsString(showDiscountDates, purchase.id));
        discountsAdded = true;
      } catch (SQLException e) {
        discountsAdded = false;
      }
    } else if (purchase.data.discounts != null || discountsAdded == false) {
      try {
        output.append("\nDiscount count: " + purchase.data.discountCount(purchase.id));
      } catch (Exception e) {
        output.append("\nDiscount count: {Error displaying count}");
      }
    }

    return output.toString();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Duplicate duplicate() {
    Purchase purchase = new Purchase();
    purchase.date = (Date) date.clone();
    purchase.products = (DataList<PurchaseProduct>) products.duplicate();
    purchase.discounts = (DataList<PurchaseDiscount>) products.duplicate();
    return null;
  }

  @Override
  public CreateInstance createInstance() {
    return new Purchase();
  }

  @Override
  public String getSelectFromQuery() {
    return """
      SELECT 
        `PurchaseID`,
        `CustomerID`,
        `Date`
      FROM `purchase` 
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
      SELECT COUNT(`purchase`.`PurchaseID`)
      FROM `purchase` 
    """;
  }

  @Override
  public String getIdColumnName() {
    return "PurchaseID";
  }

  @Override 
  public void getChildren(DataWithId<Purchase> self) {
    try {
      PurchaseProduct purchaseProductPrototype = new PurchaseProduct(null);
      products.data.clear(); 
      List<DataWithId<PurchaseProduct>> productsToInsert = purchaseProductPrototype.getList("WHERE `PurchaseID` = " + self.id, purchaseProductPrototype);
      for (DataWithId<PurchaseProduct> purchaseProduct : productsToInsert) {
        products.data.add(purchaseProduct.data);
      }
    } catch (Exception e) {}
    try {
      PurchaseDiscount purchaseDiscountPrototype = new PurchaseDiscount(null);
      discounts.data.clear(); 
      List<DataWithId<PurchaseDiscount>> productsToInsert = purchaseDiscountPrototype.getList("WHERE `PurchaseID` = " + self.id, purchaseDiscountPrototype);
      for (DataWithId<PurchaseDiscount> purchaseDiscount : productsToInsert) {
        discounts.data.add(purchaseDiscount.data);
      }
    } catch (Exception e) {}
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    date = results.getDate("Date");
  }

  @Override
  public void generatePostSql(Integer customerId, List<String> sqlCommands) {
    sqlCommands.add(String.format(
      """
      INSERT INTO `purchase` (
        `purchase`.`CustomerID`,
        `purchase`.`Date`
      ) 
      VALUES (
        %d,
        \"%s\"
      );
      """,
      customerId,
      date.toString()
    ));

    sqlCommands.add("SET @purchase_id := LAST_INSERT_ID();");

    for (PurchaseProduct productPurchase : products.data) {
      productPurchase.generatePostSql(null, sqlCommands);
    }

    for (PurchaseDiscount productDiscount : discounts.data) {
      productDiscount.generatePostSql(null, sqlCommands);
    }
  }

  @Override
  public PostResult post(Integer customerId) {
    return Post.super.post(customerId);
  }
}
