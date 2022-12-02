package cen4333.group2.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;

public class Discount implements Prototype, QueryResult, SelectFrom {
  public static final Discount PROTOTYPE_DISCOUNT = new Discount();

  public String name;
  public Date startDate;
  public Date endDate;
  public String discountJson;

  @Override
  public String toString() {
    return String.format(
      """
        Name: %s
        Start date: %s
        End date: %s
      """, 
      name,
      startDate.toLocalDate().toString(),
      endDate.toLocalDate().toString()
    );
  }

  public String toString(boolean showDates) {
    if (showDates) {
      return toString();
    } else {
      return String.format(
        """
          Name: %s
        """, 
        name
      );
    }
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
        `DiscoutJson`
      FROM 
        `discount`
        OUTER LEFT JOIN `purchase_discount` USING(`PurchaseID`)\n
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
      SELECT Count(`DiscountID`)
      FROM `discount`\n
    """;
  }

  @Override
  public String getIdColumnName() {
    return "DiscountID";
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    name = results.getString("Name");
    startDate = results.getDate("StartDate");
    endDate = results.getDate("EndDate");
    discountJson = results.getString("DiscountJson");
  }

  @Override
  public Prototype duplicate() {
    Discount discount = new Discount();
    discount.name = name;
    discount.startDate = startDate;
    discount.endDate = endDate;
    discount.discountJson = discountJson;
    return discount;
  }

  @Override
  public Prototype duplicateEmpty() {
    return new Discount();
  }
}
