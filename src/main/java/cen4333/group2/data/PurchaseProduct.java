package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class PurchaseProduct implements CreateInstance, QueryResult, SelectFrom, Duplicate {
  private Product product;

  public void setProduct(Product product) {
    this.product = product;
  }

  public PurchaseProduct(Product product) {
    this.product = product;
  }

  @Override
  public Duplicate duplicate() {
    return new PurchaseProduct((Product) product.duplicate());
  }

  @Override
  public String getSelectFromQuery() {
    return """
      SELECT
        `PurchaseID`,
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
    product.fillWithResultSet(results);    
  }

  @Override
  public CreateInstance createInstance() {
    return new PurchaseProduct(new Product());
  }
}
