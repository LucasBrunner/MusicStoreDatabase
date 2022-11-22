package cen4333.group2.junctions;

import cen4333.group2.Enums.UserType;
import cen4333.group2.journeycliengine.GoToPath;
import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;

public class ClerkJunction extends Junction {

  @Override
  public Path[] paths() {
    return new Path[] {
      new GoToPath(new CustomerJunction(UserType.CLERK))
    };
  }

  @Override
  public Junction newJunction() {
    return new ClerkJunction();
  }

  @Override
  public String getName() {
    return "Clerk Junction";
  }
  
}
