package cen4333.group2.data;

import java.math.BigDecimal;

import cen4333.group2.utility.Utility;

public class Product {
  public String name;
  public DataWithId<String> instrumentType;
  public DataWithId<String> productType;
  public BigDecimal retailPrice;
  public BigDecimal wholesalePrice;

  @Override
  public String toString() {
    return String.format(
      """
        Name: %s
        Instrument type: %s
        Product type: %s
        Retail price: %f
      """, 
      name,
      instrumentType.data,
      productType.data,
      Utility.bigDecimalAsCurrencyString(retailPrice)
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
          Wholesale price: %f
        """, 
        name,
        instrumentType.data,
        productType.data,
        Utility.bigDecimalAsCurrencyString(retailPrice),
        Utility.bigDecimalAsCurrencyString(wholesalePrice)
      );
    } else {
      return toString();
    }
  }
}
