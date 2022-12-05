package cen4333.group2.nodes.rentalnodes;

import java.text.DateFormat;
import java.text.NumberFormat;

import cen4333.group2.Node;
import cen4333.group2.data.SpaceRental;
import cen4333.group2.data.datacontainers.DataWithId;

public class ViewRentalNode extends Node {
  private DataWithId<SpaceRental> spaceRental;

  public ViewRentalNode(DataWithId<SpaceRental> spaceRental) {
    this.spaceRental = spaceRental;
  }

  @Override
  public String getName() {
    return "View rental";
  }

  @Override
  public void runNode() {
    System.out.println(String.format(
      """
      Rental Space: %s
      Customer: %s
      Start time: %s
      End time: %s
      Total cost: %s
      """,
      spaceRental.data.space.data.name,
      spaceRental.data.customer.data.person.data.fullName(),
      DateFormat.getDateTimeInstance().format(spaceRental.data.startTimestamp),
      DateFormat.getDateTimeInstance().format(spaceRental.data.endTimestamp),
      NumberFormat.getCurrencyInstance().format(spaceRental.data.totalCost())
    ));
  }
  
}
