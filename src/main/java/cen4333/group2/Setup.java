package cen4333.group2;

import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.SetupBase;
import cen4333.group2.junctions.HomeJunction;

public class Setup extends SetupBase {

  @Override
  public Junction getDefaultJunction() {
    return new HomeJunction();
  }
  
}
