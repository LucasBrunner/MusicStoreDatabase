package cen4333.group2.data;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.sqlutilities.QueryResult;
import cen4333.group2.sqlutilities.Get;
import cen4333.group2.sqlutilities.Post;
import cen4333.group2.sqlutilities.PrimaryKey;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.data.datainterfaces.Duplicate;

public class PurchaseDiscount implements Prototype<PurchaseDiscount>, QueryResult, Get<PurchaseDiscount>, Duplicate, Post<Void>, PrimaryKey {

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
  public void fillWithResultSet(ResultSet results) throws SQLException {
    discount.data.fillWithResultSet(results);
    discount.id = results.getInt(getPrimaryColumnName());
    discountAmount = results.getBigDecimal("DiscountAmount");
  }

  @Override
  public void generatePostSql(Void forignData, List<String> sqlCommands) {
    sqlCommands.add(String.format(
      """
      INSERT INTO `product_purchase` (
        `purchase_discount`.`PurchaseID`,
        `purchase_discount`.`DiscountID`,
        `purchase_discount`.`DiscountAmount`
      )
      VALUES (
        @purchase_id,
        %d,
        %s
      );
      """,
      discount.id,
      NumberFormat.getCurrencyInstance().format(discountAmount)
    ));
  }

  @Override
  public Duplicate duplicate() {
    return new PurchaseDiscount(discount);
  }

  @Override
  public PurchaseDiscount createInstance() {
    return new PurchaseDiscount(new DataWithId<Discount>(new Discount()));
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

  public static List<Integer> getIds(List<PurchaseDiscount> discounts) {
    List<Integer> output = new ArrayList<Integer>();
    for (PurchaseDiscount discountWithId : discounts) {
      output.add(discountWithId.discount.id);
    }
    return output;
  }

  @Override
  public String getPrimaryColumnName() {
    return "DiscountID";
  }
}
