package cen4333.group2.nodes;

import cen4333.group2.Main;
import cen4333.group2.Node;

public class QuitNode extends Node {

  @Override
  public String getName() {
    return "Quit app";
  }
  
  @Override
  public void runNode() {
    Main.globalData.doMainLoop = false;
  }

  @Override
  public String toString() {
    return "Quit app";
  }
}
