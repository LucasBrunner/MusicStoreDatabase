package cen4333.group2.nodes.productnodes;

import java.math.BigDecimal;
import java.sql.SQLException;

import cen4333.group2.Node;
import cen4333.group2.data.Product;
import cen4333.group2.data.datacontainers.DataString;
import cen4333.group2.sqlutilities.GetIter;
import cen4333.group2.utility.UserInput;

public class CreateProductNode extends Node {

  @Override
  public String getName() {
    return "Create product";
  }

  @Override
  public void runNode() {
    try {
      Product product = new Product();
      System.out.print("Enter the name for the product: ");
      product.name = UserInput.getString();
  
      System.out.println("Select an instrument type for the product:");
      product.instrumentType = new GetIter<DataString>(
        "",
        Product.PROTOTYPE_INSTRUMENT_TYPE,
        GetIter.getResultsAmount()
      ).userSelect(false, "instrument type", false);
  
      System.out.println("Select a product type for the product:");
      product.productType = new GetIter<DataString>(
        "",
        Product.PROTOTYPE_PRODUCT_TYPE,
        GetIter.getResultsAmount()
      ).userSelect(false, "product type", false);

      System.out.print("Enter the retail price of the product: $");
      product.retailPrice = new BigDecimal(UserInput.getDouble());

      System.out.print("Enter the wholesale price of the product: $");
      product.wholesalePrice = new BigDecimal(UserInput.getDouble());

      System.out.println("Inserting product...");
      product.post(null);
      System.out.println("Insert successful.");
    } catch (SQLException e) {
      System.out.println("Error! Something went wrong.");
    }
  }
  
}
