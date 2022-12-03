package cen4333.group2.data;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class PurchaseDiscount implements CreateInstance, QueryResult, SelectFrom, Duplicate {

  public DataWithId<Discount> discount;
  public BigDecimal discountAmount;

  public PurchaseDiscount(DataWithId<Discount> discount) {
    this.discount = discount;
  }

  @Override
  public String getSelectFromQuery() {
    return """
      SELECT 
        `PurchaseID`,
        `DiscountAmount`,
        `DiscountID`,
        `Name`,
        `StartDate`,
        `EndDate`,
        `DiscountJson`
      FROM 
        `discount`
        INNER JOIN `purchase_discount` USING(`DiscountID`)
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
      SELECT Count(`DiscountID`)
      FROM 
        `discount`
        INNER JOIN `purchase_discount` USING(`DiscountID`)
    """;
  }

  @Override
  public String getIdColumnName() {
    return "DiscountID";
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    discount.data.fillWithResultSet(results);
    discount.id = results.getInt(getIdColumnName());
    discountAmount = results.getBigDecimal("DiscountAmount");
  }

  @Override
  public Duplicate duplicate() {
    return new PurchaseDiscount(discount);
  }

  @Override
  public CreateInstance createInstance() {
    return new PurchaseDiscount(new DataWithId<Discount>());
  }

  @Override
  public String toString() {
    return (discount.toString() + getDiscountAmountIfAny()).trim();
  }

  public String toString(boolean showDates) {
    if (showDates) {
      return toString();
    } else {
      return (discount.data.toString(showDates) + getDiscountAmountIfAny()).trim();
    }
  }

  public String getDiscountAmountIfAny() {
    if (discountAmount == null) {
      return "";
    } else {
      return "\nDiscount amount: " + NumberFormat.getCurrencyInstance().format(discountAmount);
    }
  }
}
