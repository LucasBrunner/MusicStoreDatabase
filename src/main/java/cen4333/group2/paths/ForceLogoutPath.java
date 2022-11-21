package cen4333.group2.paths;

import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;
import cen4333.group2.junctions.HomeJunction;

public class ForceLogoutPath extends Path {

  private String message;

  public ForceLogoutPath() {}

  public ForceLogoutPath(String message) {
    this.message = message;
  }

  @Override
  public String name() {
    // TODO Auto-generated method stub
    return "Forced Logout";
  }

  @Override
  public Junction junction() {
    return new HomeJunction();
  }

  @Override
  public void run() {
    if (message != null) {
      System.out.println(message);
    }
  }
  
}
