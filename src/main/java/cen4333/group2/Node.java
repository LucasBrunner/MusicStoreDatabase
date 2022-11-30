package cen4333.group2;

public abstract class Node {
  public abstract String getName();

  public void runNode() {};

  @Override
  public String toString() {
    return getName();
  }
}
