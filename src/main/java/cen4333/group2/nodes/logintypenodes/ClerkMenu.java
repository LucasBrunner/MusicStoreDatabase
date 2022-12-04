package cen4333.group2.nodes.logintypenodes;

import cen4333.group2.Node;
import cen4333.group2.utility.ObjectSelector;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.LogoutNode;
import cen4333.group2.nodes.customernodes.CreateCustomerNode;
import cen4333.group2.nodes.customernodes.SearchCustomersNode;

public class ClerkMenu extends Node {

  @Override
  public String getName() {
    return "Clerk";
  }

  @Override
  public void runNode() {
    while (true) {
      try {
        Node n = ObjectSelector.printAndGetSelection(new Node[] {
          new SearchCustomersNode(),
          new CreateCustomerNode(),
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
