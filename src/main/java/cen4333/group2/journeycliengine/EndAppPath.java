package cen4333.group2.journeycliengine;

public class EndAppPath extends Path {

  @Override
  public String name() {
    return "Close App";
  }

  @Override
  public Junction junction() {
    return null;
  }

  @Override
  public void run() {
    System.out.println("Closing app...");
  }
  
}
