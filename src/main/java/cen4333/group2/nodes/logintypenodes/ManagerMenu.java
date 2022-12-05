package cen4333.group2.nodes.logintypenodes;

import cen4333.group2.Node;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.LogoutNode;
import cen4333.group2.nodes.productnodes.CreateDiscountNode;
import cen4333.group2.nodes.productnodes.CreateProductNode;
import cen4333.group2.nodes.rentalnodes.CreateSpaceNode;
import cen4333.group2.utility.ObjectSelector;

public class ManagerMenu extends Node {

  @Override
  public String getName() {
    return "Manager";
  }

  @Override
  public void runNode() {
    while (true) {
      try {
        Node n = ObjectSelector.printAndGetSelection(new Node[] {
          new CreateProductNode(),
          new CreateDiscountNode(),
          new CreateSpaceNode(),
          new LogoutNode()
        });

        if (n.getClass() == LogoutNode.class) {
          break;
        } else {
          n.runNode();
        }
      } catch (NoItemsException e) {
        e.printStackTrace();
      }
    }
  }
  
}
