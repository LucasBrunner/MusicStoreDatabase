package cen4333.group2.junctions.customerjunctions;

import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;

public class AddCustomerJunction extends Junction {

  @Override
  public Path[] paths() {
    return null;
  }

  @Override
  public Junction newJunction() {
    return new AddCustomerJunction();
  }

  @Override
  public String getName() {
    return "Add Customer";
  }
  
}
