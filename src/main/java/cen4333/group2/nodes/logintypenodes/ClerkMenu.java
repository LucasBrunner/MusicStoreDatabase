package cen4333.group2.nodes.logintypenodes;

import cen4333.group2.Node;
import cen4333.group2.utility.Utility;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.LogoutNode;
import cen4333.group2.nodes.customernodes.SearchCustomersNode;

public class ClerkMenu extends Node {

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return "Clerk";
  }

  @Override
  public void runNode() {
    while (true) {
      try {
        Node n = Utility.printAndGetSelection(new Node[] {
          new SearchCustomersNode(),
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
