package cen4333.group2.junctions;

import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;

public class ManagerJunction extends Junction {

  @Override
  public Path[] paths() {
    return null;
  }

  @Override
  public Junction newJunction() {
    return new ManagerJunction();
  }

  @Override
  public String getName() {
    return "Manager Junction";
  }
  
}
