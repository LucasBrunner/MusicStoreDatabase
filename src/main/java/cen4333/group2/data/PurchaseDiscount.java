package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class PurchaseDiscount implements CreateInstance, QueryResult, SelectFrom, Duplicate {

  public Discount discount;

  public PurchaseDiscount(Discount discount) {
    this.discount = discount;
  }

  @Override
  public String getSelectFromQuery() {
    return """
      SELECT 
        `PurchaseID`,
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
    discount.fillWithResultSet(results);
  }

  @Override
  public Duplicate duplicate() {
    return new PurchaseDiscount(discount);
  }

  @Override
  public CreateInstance createInstance() {
    return new Discount();
  }
}
