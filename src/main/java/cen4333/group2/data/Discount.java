package cen4333.group2.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class Discount implements CreateInstance, Duplicate, QueryResult, SelectFrom {
  public static final Discount CreateInstance_DISCOUNT = new Discount();

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
    ).trim();
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
        `DiscountID`,
        `Name`,
        `StartDate`,
        `EndDate`,
        `DiscountJson`
      FROM 
        `discount`
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
      SELECT Count(`DiscountID`)
      FROM `discount`
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
  public Duplicate duplicate() {
    Discount discount = new Discount();
    discount.name = name;
    discount.startDate = startDate;
    discount.endDate = endDate;
    discount.discountJson = discountJson;
    return discount;
  }

  @Override
  public CreateInstance createInstance() {
    return new Discount();
  }
}
