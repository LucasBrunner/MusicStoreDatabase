package cen4333.group2.data;

import java.math.BigDecimal;

public class Product {
  public String name;
  public DataWithId<String> instrumentType;
  public DataWithId<String> productType;
  public BigDecimal retailPrice;
  public BigDecimal wholesalePrice;
}
