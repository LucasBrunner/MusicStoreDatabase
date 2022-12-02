package cen4333.group2.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;

public class Purchase implements QueryResult, SelectFrom, Prototype, GetInfo {
  public enum CountOrValues {
    Count,
    Values
  }

  public Date date;
  public DataList<Product> products;
  public DataList<Discount> discounts;

  public String productsAsString(boolean showWholesalePrice) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < products.data.size(); i++) {
      String product = products.data.get(i).toString(showWholesalePrice);
      product = product.replace("\n", "\n  ");
      output.append("Product " + (i + 1) + ":\n  ");
      output.append(product);
      output.append("\n");
    }
    return output.toString();
  }

  public String discountsAsString(boolean showDates) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < discounts.data.size(); i++) {
      String discount = discounts.data.get(i).toString(showDates);
      discount = discount.replace("\n", "\n  ");
      output.append("Discount " + (i + 1) + ":\n  ");
      output.append(discount);
      output.append("\n");
    }
    return output.toString();
  }

  public static String toString(DataWithId<Purchase> purchase, CountOrValues showProducts, boolean showWholesalePrices, CountOrValues showDiscounts, boolean showDiscountDates) {
    StringBuilder output = new StringBuilder();
    output.append("Date of purchase: " + purchase.data.date.toString());

    // products
    if (showProducts == CountOrValues.Count) {
      int productCount; 
      if (purchase.data.products != null) {
        productCount = purchase.data.products.data.size();
      } else {
        try {
          productCount = Product.PROTOTYPE_PRODUCT.getCount("WHERE 'PurchaseID' = " + purchase.id);
        } catch (Exception e) {
          productCount = 0;
        }
      }
      output.append("\nProduct count: " + productCount);
    } else {
      output.append("\n" + purchase.data.productsAsString(showWholesalePrices));
    }

    // discounts
    if (showDiscounts == CountOrValues.Count) {
      int discountCount; 
      if (purchase.data.discounts != null) {
        discountCount = purchase.data.discounts.data.size();
      } else {
        try {
          discountCount = Discount.PROTOTYPE_DISCOUNT.getCount("WHERE 'PurchaseID' = " + purchase.id);
        } catch (Exception e) {
          discountCount = 0;
        }
      }
      output.append("\nDiscount count: " + discountCount);
    } else {
      output.append("\n" + purchase.data.discountsAsString(showDiscountDates));
    }

    return output.toString();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Prototype duplicate() {
    Purchase purchase = new Purchase();
    purchase.date = (Date) date.clone();
    purchase.products = (DataList<Product>) products.duplicate();
    purchase.discounts = (DataList<Discount>) products.duplicate();
    return null;
  }

  @Override
  public Prototype duplicateEmpty() {
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
