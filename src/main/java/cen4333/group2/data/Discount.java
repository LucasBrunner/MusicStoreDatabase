package cen4333.group2.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.sqlutilities.QueryResult;
import cen4333.group2.sqlutilities.Get;
import cen4333.group2.sqlutilities.GetIter;
import cen4333.group2.sqlutilities.PrimaryKey;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datacontainers.ObjectWithValue;
import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.data.datainterfaces.DisplayText;
import cen4333.group2.data.datainterfaces.Duplicate;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.utility.ObjectSelector;
import cen4333.group2.utility.UserInput;

public class Discount implements Prototype<Discount>, Duplicate, QueryResult, Get<Discount>, DisplayText, PrimaryKey {
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
  public Discount createInstance() {
    return new Discount();
  }

  @Override
  public String getDisplayText() {
    return "Name: " + name;
  }

  public static String getWhereStatement(boolean doShowUnavailableDiscounts) {  
    String now = new Date(Instant.now().getEpochSecond()).toString();  
    return String.format(
      """
      WHERE
        AND `StartDate` < \"%s\"
        AND `EndDate` > \"%s\"
      """,
      now,
      now
    );
  }

  public static DataWithId<Discount> selectDiscount(boolean doShowUnavailableDiscounts, List<String> whereClauses) throws SQLException {   
    StringBuilder whereClause = new StringBuilder(getWhereStatement(doShowUnavailableDiscounts));
    if (whereClauses != null && whereClauses.size() > 0) {
      for (String clause : whereClauses) {
        whereClause.append("\nAND ");
        whereClause.append(clause);
      }
    }
    return new GetIter<Discount>(
      whereClause.toString(), 
      new Discount(), 
      GetIter.getResultsAmount()
    ).userSelect(
      true, 
      "product", 
      true
    );
  }

  public static DataWithId<Discount> searchDiscounts(String discountName, boolean doShowUnavailableDiscounts, List<String> whereClauses) throws SQLException {
    whereClauses.add("`Name` LIKE \"%" + discountName + "%\"");
    StringBuilder whereClause = new StringBuilder(getWhereStatement(doShowUnavailableDiscounts));
    if (whereClauses != null && whereClauses.size() > 0) {
      for (String clause : whereClauses) {
        whereClause.append("\nAND ");
        whereClause.append(clause);
      }
    }
    return new GetIter<Discount>(
      whereClause.toString(),
      new Discount(), 
      GetIter.getResultsAmount()
    ).userSelect(
      true, 
      "product", 
      false
    );
  }

  public static DataWithId<Discount> searchDiscounts(int discountId, boolean doShowUnavailableDiscounts, List<String> whereClauses) throws SQLException {
    whereClauses.add("`DiscountID` = " + discountId);
    StringBuilder whereClause = new StringBuilder(getWhereStatement(doShowUnavailableDiscounts));
    if (whereClauses != null && whereClauses.size() > 0) {
      for (String clause : whereClauses) {
        whereClause.append("\nAND ");
        whereClause.append(clause);
      }
    }
    return new GetIter<Discount>(
      whereClause.toString(),
      new Discount(), 
      1
    ).userSelect(
      true, 
      "product", 
      true
    );
  }

  public static DataWithId<Discount> searchDiscountsAllMethods(boolean doShowUnavailableDiscounts, List<String> whereClauses) throws SQLException {
    List<ObjectWithValue<String, Integer>> selectionList = new ArrayList<ObjectWithValue<String, Integer>>();
    selectionList.add(new ObjectWithValue<String,Integer>("Search by name", 1));
    selectionList.add(new ObjectWithValue<String,Integer>("Search by ID", 2));
    selectionList.add(new ObjectWithValue<String,Integer>("View all", 3));
    int selction = -1;
    try {
      selction = ObjectSelector.printAndGetSelection(selectionList).value;
    } catch (NoItemsException e) {}

    switch (selction) {
      case 1:
        System.out.print("Enter the name you would like to search: ");
        return searchDiscounts(UserInput.getString(), doShowUnavailableDiscounts, whereClauses);

      case 2:
        System.out.print("Enter the ID you would like to search: ");
        return searchDiscounts(UserInput.getInt(), doShowUnavailableDiscounts, whereClauses);

      case 3:
        return selectDiscount(doShowUnavailableDiscounts, whereClauses);
    
      default:
        System.out.println("Invalid state! Returning to valid state...");
        return null;
    }
  }

  @Override
  public String getPrimaryColumnName() {
    return "DiscountID";
  }
}
