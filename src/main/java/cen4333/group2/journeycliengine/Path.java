package cen4333.group2.journeycliengine;

public abstract class Path {
  public abstract String name();

  public final String getName() {
    String name = name();
    if (name == null || name.isBlank()) {
      return "Unnamed Path";
    } else {
      return name;
    }
  }

  public abstract Junction junction();

  public final Junction getJunction(SetupBase setup) {
    Junction junction = junction();
    if (junction != null) {
      return junction;
    } else {
      return setup.getDefaultJunction();
    }
  }

  public abstract void run();  

  private Junction previousJunction;

  public void setPreviousJunction(Junction previousJunction) {
    this.previousJunction = previousJunction;
  }

  public Junction getPreviousJunction() {
    return previousJunction;
  }
}
