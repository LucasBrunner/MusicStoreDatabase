package cen4333.group2.junctions;

import cen4333.group2.journeycliengine.EndAppPath;
import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;
import cen4333.group2.paths.EchoPath;

public class HomeJunction extends Junction {

  @Override
  public Path[] paths() {
    return new Path[] {
      new EchoPath(),
      new EndAppPath()
    };
  }

  @Override
  public Junction newJunction() {
    return new HomeJunction();
  }
  
}
