package cen4333.group2.paths;

import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;
import cen4333.group2.journeycliengine.UserInput;

public class EchoPath extends Path {

  private Junction gotoJunction;

  public EchoPath() {}

  public EchoPath(Junction gotoJunction) {
    this.gotoJunction = gotoJunction;
  }

  @Override
  public String name() {
    return "Echo";
  }

  @Override
  public Junction junction() {
    if (gotoJunction != null) {
      return gotoJunction;
    } else {
      return getPreviousJunction().newJunction();
    }
  }

  @Override
  public void run() {
    System.out.print("Enter anything: ");
    String text = UserInput.getString();
    System.out.printf("\n\"%s\"\n", text);
  }
  
}
