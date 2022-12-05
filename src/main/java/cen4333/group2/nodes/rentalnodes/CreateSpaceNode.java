package cen4333.group2.nodes.rentalnodes;

import java.math.BigDecimal;

import cen4333.group2.Node;
import cen4333.group2.data.Space;
import cen4333.group2.utility.UserInput;

public class CreateSpaceNode extends Node {

  @Override
  public String getName() {
    return "Create space";
  }

  @Override
  public void runNode() {
    Space space = Space.createInstanceStatic();
    System.out.print("Enter the name for the product: ");
    space.name = UserInput.getString();
 
    System.out.println("Enter the start date for the discount:");
    space.hourlyCost = new BigDecimal(UserInput.getDouble());

    System.out.println("Inserting discount...");
    space.post(null);
    System.out.println("Insert successful.");
  } 
  
}
