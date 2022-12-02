package cen4333.group2.data;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;
import cen4333.group2.data.datacontainers.DataString;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class Product implements CreateInstance, QueryResult, SelectFrom, Duplicate {

  public static final Product CreateInstance_PRODUCT = new Product();

  public String name;
  public DataWithId<DataString> instrumentType;
  public DataWithId<DataString> productType;
  public BigDecimal retailPrice;
  public BigDecimal wholesalePrice;

  @Override
  public String toString() {
    return String.format(
      """
        Name: %s
        Instrument type: %s
        Product type: %s
        Retail price: %f\n
      """, 
      name,
      instrumentType.data,
      productType.data,
      NumberFormat.getCurrencyInstance().format(retailPrice)
    );
  }

  public String toString(boolean showWholesalePrice) {
    if (showWholesalePrice) {
      return String.format(
        """
          Name: %s
          Instrument type: %s
          Product type: %s
          Retail price: %f
          Wholesale price: %f\n
        """, 
        name,
        instrumentType.data,
        productType.data,
        NumberFormat.getCurrencyInstance().format(retailPrice),
        NumberFormat.getCurrencyInstance().format(wholesalePrice)
      );
    } else {
      return toString();
    }
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    name = results.getString("Name");
    instrumentType = new DataWithId<DataString>();
    instrumentType.id = results.getInt("InstrumentTypeID");
    instrumentType.data = new DataString(results.getString("`product_type`.`Type`"));
    productType = new DataWithId<DataString>();
    productType.id = results.getInt("ProductTypeID");
    productType.data = new DataString(results.getString("`product_type`.`Type`"));
    retailPrice = results.getBigDecimal("RetailPrice");
    wholesalePrice = results.getBigDecimal("WholesalePrice");
  }

  @SuppressWarnings("unchecked")
  @Override
  public Duplicate duplicate() {
    Product product = new Product();
    product.name = name;
    product.instrumentType = (DataWithId<DataString>) instrumentType.duplicate();
    product.productType = (DataWithId<DataString>) productType.duplicate();
    product.retailPrice = retailPrice;
    product.wholesalePrice = wholesalePrice;
    return product;
  }

  @Override
  public CreateInstance createInstance() {
    return new Product();
  }

  @Override
  public String getSelectFromQuery() {
    return """
      SELECT
        `PurchaseID`,
        `ProductID`,
        `Name`,
        `InsturmentTypeID`,
        `instrument_type`.`Type`,
        `ProductTypeID`,
        `product_type`.`Type`,
        `RetailPrice`,
        `WholesalePrice`
      FROM
        `product`
        INNER JOIN `instrument_type` USING(`InsturmentTypeID`)
        INNER JOIN `product_type` USING(`ProductTypeID`)
        OUTER LEFT JOIN `product_purchase` USING(`PurchaseID`)
        
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
      SELECT COUNT(ProductID)
      FROM product  
    """;
  }

  @Override
  public String getIdColumnName() {
    return "ProductID";
  }
}
