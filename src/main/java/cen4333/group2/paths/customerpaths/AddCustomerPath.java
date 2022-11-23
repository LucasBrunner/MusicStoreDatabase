package cen4333.group2.paths.customerpaths;

import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;

public class AddCustomerPath extends Path {

  @Override
  public String name() {
    return "Add Customer";
  }

  @Override
  public Junction junction() {
    return this.getPreviousJunction();
  }

  @Override
  public void run() {
    System.out.print("Enter");
  }
  
}
