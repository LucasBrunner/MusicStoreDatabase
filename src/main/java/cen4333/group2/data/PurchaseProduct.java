package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.Get;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class PurchaseProduct implements CreateInstance, QueryResult, Get, Duplicate {
  public DataWithId<Product> product;
  public int amountOfProducts = 0;

  public PurchaseProduct(DataWithId<Product> product) {
    this.product = product;
  }

  public PurchaseProduct(DataWithId<Product> product, int amountOfProducts) {
    this.product = product;
    this.amountOfProducts = amountOfProducts;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Duplicate duplicate() {
    return new PurchaseProduct((DataWithId<Product>) product.duplicate(), amountOfProducts);
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
    amountOfProducts = results.getInt("ProductCount");
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
      product.toString().trim(),
      amountOfProducts
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
        amountOfProducts
      ).trim();
    } else {
      return toString();
    }
  }
}
