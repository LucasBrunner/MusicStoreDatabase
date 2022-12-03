package cen4333.group2.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;
import cen4333.group2.data.datacontainers.DataList;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class Purchase implements QueryResult, SelectFrom, CreateInstance, Duplicate {
  public enum CountOrValues {
    Count,
    Values
  }

  public Date date;
  private DataList<PurchaseProduct> products;
  private DataList<PurchaseDiscount> discounts;

  public DataList<PurchaseProduct> getProducts(int purchaseId) throws SQLException {
    if (products == null) {
      products = new DataList<PurchaseProduct>();
      ResultSet results = new PurchaseProduct(null).getSelectFrom("WHERE `PurchaseID` = " + purchaseId);
      while (results.next()) {
        PurchaseProduct product = new PurchaseProduct(new DataWithId<Product>());
        product.fillWithResultSet(results);
        products.data.add(product);
      }
    }
    return products;
  }

  public DataList<PurchaseDiscount> getDiscounts(int purchaseId) throws SQLException {
    if (discounts == null) {
      discounts = new DataList<PurchaseDiscount>();
      ResultSet results = new PurchaseDiscount(null).getSelectFrom("WHERE `PurchaseID` = " + purchaseId);
      while (results.next()) {
        PurchaseDiscount discount = new PurchaseDiscount(new Discount());
        discount.fillWithResultSet(results);
        discounts.data.add(discount);
      }
    }
    return discounts;
  }

  public DataList<PurchaseProduct> getProductsOrNull() {
    return products;
  }

  public DataList<PurchaseDiscount> getDiscountsOrNull() {
    return discounts;
  }

  public String productsAsString(boolean showWholesalePrice, int purchaseId) throws SQLException {
    StringBuilder output = new StringBuilder();
    List<PurchaseProduct> products = getProducts(purchaseId).data;
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
    List<PurchaseDiscount> discounts = getDiscounts(purchaseId).data;
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
    if (products == null) {
      getProducts(purchaseId);
    }
    return products.data.size();
  }

  public int discountCount(int purchaseId) throws SQLException {
    if (discounts == null) {
      getDiscounts(purchaseId);
    } 
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
  public void fillWithResultSet(ResultSet results) throws SQLException {
    date = results.getDate("Date");
  }
}
