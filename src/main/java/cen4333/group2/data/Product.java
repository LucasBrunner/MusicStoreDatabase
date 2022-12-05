package cen4333.group2.data;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.sqlutilities.QueryResult;
import cen4333.group2.sqlutilities.Get;
import cen4333.group2.sqlutilities.GetIter;
import cen4333.group2.sqlutilities.Post;
import cen4333.group2.sqlutilities.PrimaryKey;
import cen4333.group2.data.datacontainers.DataString;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datacontainers.ObjectWithValue;
import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.data.datainterfaces.DisplayText;
import cen4333.group2.data.datainterfaces.Duplicate;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.utility.ObjectSelector;
import cen4333.group2.utility.UserInput;

public class Product implements Prototype<Product>, QueryResult, Get<Product>, Duplicate, DisplayText, PrimaryKey, Post<Void> {

  public static final Product PROTOTYPE_PRODUCT = new Product();
  public static final DataString PROTOTYPE_INSTRUMENT_TYPE = new DataString(
    "", 
    "InstrumentTypeID", 
    "Instrument type: ", 
    "SELECT InstrumentTypeID, Type FROM `instrument_type`",
    "SELECT COUNT(InstrumentTypeID) FROM `instrument_type`");
  public static final DataString PROTOTYPE_PRODUCT_TYPE = new DataString(
    "", 
    "ProductTypeID", 
    "Product type: ", 
    "SELECT ProductTypeID, Type FROM `product_type`",
    "SELECT COUNT(ProductTypeID) FROM `product_type`");

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
      Retail price: %s
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
        Retail price: %s
        Wholesale price: %s
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
    instrumentType.data = new DataString(results.getString("InstrumentType"));
    productType = new DataWithId<DataString>();
    productType.id = results.getInt("ProductTypeID");
    productType.data = new DataString(results.getString("ProductType"));
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
  public Product createInstance() {
    return new Product();
  }

  @Override
  public String getSelectFromQuery() {
    return """
      SELECT
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
  public String getDisplayText() {
    return "Name: " + name;
  }

  public static DataWithId<Product> selectProduct(List<String> whereClauses) throws SQLException {  
    StringBuilder whereClause = new StringBuilder("WHERE 1 = 1");
    if (whereClauses != null && whereClauses.size() > 0) {
      for (String clause : whereClauses) {
        whereClause.append("\nAND ");
        whereClause.append(clause);
      }
    }  
    return new GetIter<Product>(
      whereClause.toString(), 
      new Product(), 
      GetIter.getResultsAmount()
    ).userSelect(
      true, 
      "product", 
      true
    );
  }

  public static DataWithId<Product> searchProducts(String productName, List<String> whereClauses) throws SQLException { 
    StringBuilder whereClause = new StringBuilder("WHERE\n`Name` LIKE \"%" + productName + "%\"");
    if (whereClauses != null && whereClauses.size() > 0) {
      for (String clause : whereClauses) {
        whereClause.append("\nAND ");
        whereClause.append(clause);
      }
    }     
    return new GetIter<Product>(
      whereClause.toString(), 
      new Product(), 
      GetIter.getResultsAmount()
    ).userSelect(
      true, 
      "product", 
      false
    );
  }

  public static DataWithId<Product> searchProducts(int productId, List<String> whereClauses) throws SQLException {  
    StringBuilder whereClause = new StringBuilder("WHERE `ProductID` = " + productId);
    if (whereClauses != null && whereClauses.size() > 0) {
      for (String clause : whereClauses) {
        whereClause.append("\nAND ");
        whereClause.append(clause);
      }
    }    
    return new GetIter<Product>(
      whereClause.toString(), 
      new Product(), 
      1
    ).userSelect(
      true, 
      "product", 
      true
    );
  }

  public static DataWithId<Product> searchProductsAllMethods(List<String> whereClauses) throws SQLException {
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
        return searchProducts(UserInput.getString(), whereClauses);

      case 2:
        System.out.print("Enter the ID you would like to search: ");
        return searchProducts(UserInput.getInt(), whereClauses);

      case 3:
        return selectProduct(whereClauses);
    
      default:
        System.out.println("Invalid state! Returning to valid state...");
        return null;
    }
  }

  @Override
  public String getPrimaryColumnName() {
    return "ProductID";
  }

  @Override
  public void generatePostSql(Void forignData, List<String> sqlCommands) {
    sqlCommands.add(String.format(
      """
      INSERT INTO `product`
      (
        `Name`,
        `InstrumentTypeID`,
        `ProductTypeID`,
        `RetailPrice`,
        `WholesalePrice`
      )
      VALUES
      (
        \"%s\",
        %d,
        %d,
        %s,
        %s
      );
      """,
      name,
      instrumentType.id,
      productType.id,
      retailPrice.toString(),
      wholesalePrice.toString()
    ));    
  }
}
