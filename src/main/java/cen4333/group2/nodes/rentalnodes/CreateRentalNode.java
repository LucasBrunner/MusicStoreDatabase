package cen4333.group2.nodes.rentalnodes;

import java.sql.SQLException;
import java.sql.Timestamp;

import cen4333.group2.Node;
import cen4333.group2.data.Customer;
import cen4333.group2.data.Space;
import cen4333.group2.data.SpaceRental;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.sqlutilities.GetIter;
import cen4333.group2.utility.UserInput;

public class CreateRentalNode extends Node {
  private DataWithId<Customer> customerWithId;

  public CreateRentalNode(DataWithId<Customer> customerWithId) {
    this.customerWithId = customerWithId;
  }

  @Override
  public String getName() {
    return "Create rental";
  }

  @Override
  public void runNode() {
    System.out.println("Pick a space to rent out:");
    DataWithId<Space> spaceWithId;
    try {
      spaceWithId = new GetIter<Space>("", Space.createInstanceStatic(), GetIter.getResultsAmount()).userSelect(false, "space", false);

      System.out.println("What is the start date/time of the rental?");
      Timestamp startTime = new Timestamp(UserInput.getSqlDate("YYYY-MM-DD HH:mm").getTime());
      System.out.println("What is the end date/time of the rental?");
      Timestamp endTime = new Timestamp(UserInput.getSqlDate("YYYY-MM-DD HH:mm").getTime());
  
      SpaceRental spaceRental = new SpaceRental(spaceWithId, customerWithId, startTime, endTime);

      System.out.println("Inserting rental...");
      spaceRental.post(null);
      System.out.println("Insert successful!\n");
    } catch (SQLException e) {
      System.out.println("Error! Could not get spaces.");
    }
  }
  
}
