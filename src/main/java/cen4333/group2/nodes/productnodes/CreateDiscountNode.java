package cen4333.group2.nodes.productnodes;

import cen4333.group2.Node;
import cen4333.group2.data.Discount;
import cen4333.group2.utility.UserInput;

public class CreateDiscountNode extends Node {

  @Override
  public String getName() {
    return "Create discount";
  }

  @Override
  public void runNode() {
    Discount discount = new Discount();
    System.out.print("Enter the name for the product: ");
    discount.name = UserInput.getString();
 
    System.out.println("Enter the start date for the discount:");
    discount.startDate = UserInput.getSqlDate("YYYY-MM-DD");
 
    System.out.println("Enter the end date for the discount:");
    discount.endDate = UserInput.getSqlDate("YYYY-MM-DD");

    System.out.print("Enter the discount's JSON: ");
    discount.discountJson = UserInput.getString();

    System.out.println("Inserting discount...");
    discount.post(null);
    System.out.println("Insert successful.");
  }  
}
