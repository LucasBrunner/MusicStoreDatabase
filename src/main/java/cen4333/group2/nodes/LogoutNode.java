package cen4333.group2.nodes;

import cen4333.group2.Main;
import cen4333.group2.Node;

public class LogoutNode extends Node {

  @Override
  public String getName() {
    return "Log out";
  }

  @Override
  public void runNode() {
    Main.globalData.clear();
  }
  
}
