package cen4333.group2.nodes;

import cen4333.group2.Node;

public class NothingNode extends Node {

  private String nameText;

  public NothingNode(String nameText) {
    this.nameText = nameText;
  }

  public NothingNode() {
    this("Do nothing");
  }

  @Override
  public String getName() {
    return nameText;
  }
  
}
