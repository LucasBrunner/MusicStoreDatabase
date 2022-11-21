package cen4333.group2.journeycliengine;

public class GoToPath extends Path {

  private Junction nextJunction;

  public GoToPath(Junction nextJunction) {
    this.nextJunction = nextJunction;
  }

  @Override
  public String name() {
    return "Go to " + nextJunction.getName();
  }

  @Override
  public Junction junction() {
    return nextJunction;
  }

  @Override
  public void run() {}
  
}
