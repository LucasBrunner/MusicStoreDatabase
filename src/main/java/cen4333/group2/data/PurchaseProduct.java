package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.Get;
import cen4333.group2.daos.sqlutilities.Post;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class PurchaseProduct implements CreateInstance, QueryResult, Get<PurchaseProduct>, Duplicate, Post<Void> {
  public DataWithId<Product> product;
  public int productCount = 0;

  public PurchaseProduct(DataWithId<Product> product) {
    this.product = product;
  }

  public PurchaseProduct(DataWithId<Product> product, int amountOfProducts) {
    this.product = product;
    this.productCount = amountOfProducts;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Duplicate duplicate() {
    return new PurchaseProduct((DataWithId<Product>) product.duplicate(), productCount);
  }

  @Override
  public String getSelectFromQuery() {
    return """
    SELECT
      `PurchaseID`,
      `ProductCount`,
      `ProductID`,
      `Name`,
      `InstrumentTypeID`,
      `instrument_type`.`Type` AS 'InstrumentType',
      `ProductTypeID`,
      `product_type`.`Type` AS 'ProductType',
      `RetailPrice`,
      `WholesalePrice`
    FROM
      `product`
      INNER JOIN `instrument_type` USING(`InstrumentTypeID`)
      INNER JOIN `product_type` USING(`ProductTypeID`)
      INNER JOIN `product_purchase` USING(`ProductID`)        
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
    SELECT COUNT(ProductID)
    FROM 
      product  
      INNER JOIN `product_purchase` USING(`ProductID`)  
    """;
  }

  @Override
  public String getIdColumnName() {
    return "ProductID";
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    product.data.fillWithResultSet(results);
    product.id = results.getInt(getIdColumnName());
    productCount = results.getInt("ProductCount");
  }

  @Override
  public CreateInstance createInstance() {
    return new PurchaseProduct(new DataWithId<Product>(new Product()));
  }

  @Override
  public String toString() {
    return String.format(
      """
      %s
      Product count: %s
      """, 
      product.data.toString().trim(),
      productCount
    ).trim();
  }

  public String toString(boolean showWholesalePrice) {
    if (showWholesalePrice) {
      return String.format(
        """
        %s
        Product count: %s
        """, 
        product.data.toString(showWholesalePrice).trim(),
        productCount
      ).trim();
    } else {
      return toString();
    }
  }

  /**
   * Expects the MySQL variable `@purchase_id` to be set to the id of the purchase this product is associated with.
   * @param forignData
   * @param sqlCommands
   */
  @Override
  public void generatePostSql(Void forignData, List<String> sqlCommands) {
    sqlCommands.add(String.format(
      """
      INSERT INTO `product_purchase` (
        `product_purchase`.`PurchaseID`,
        `product_purchase`.`ProductID`,
        `product_purchase`.`ProductCount`
      )
      VALUES (
        @purchase_id,
        %d,
        %d
      );
      """,
      product.id,
      productCount
    ));
  }
}
