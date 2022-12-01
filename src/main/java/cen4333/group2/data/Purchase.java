package cen4333.group2.data;

import java.sql.Date;
import java.util.List;

public class Purchase {
  public Date date;
  public List<Product> products;
  public List<Discount> discounts;

  public String productsAsString(boolean showWholesalePrice) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < products.size(); i++) {
      String product = products.get(i).toString(showWholesalePrice);
      product = product.replace("\n", "\n  ");
      output.append("Product " + (i + 1) + ":\n  ");
      output.append(product);
      output.append("\n");
    }
    return output.toString();
  }

  public String discountsAsString(boolean showDates) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < discounts.size(); i++) {
      String discount = discounts.get(i).toString(showDates);
      discount = discount.replace("\n", "\n  ");
      output.append("Discount " + (i + 1) + ":\n  ");
      output.append(discount);
      output.append("\n");
    }
    return output.toString();
  }
}
