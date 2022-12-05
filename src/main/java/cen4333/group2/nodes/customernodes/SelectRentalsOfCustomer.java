package cen4333.group2.nodes.customernodes;

import java.sql.SQLException;

import cen4333.group2.Node;
import cen4333.group2.data.Customer;
import cen4333.group2.data.SpaceRental;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.nodes.rentalnodes.ViewRentalNode;
import cen4333.group2.sqlutilities.GetIter;

public class SelectRentalsOfCustomer extends Node {
  private DataWithId<Customer> customerWithId;

  public SelectRentalsOfCustomer(DataWithId<Customer> customerWithId) {
    this.customerWithId = customerWithId;
  }

  @Override
  public String getName() {
    return "View rentals";
  }

  @Override
  public void runNode() {
    int pageSize = GetIter.getResultsAmount();
    while (true) {
      try {
        DataWithId<SpaceRental> spaceRentalWithId = new GetIter<SpaceRental>(
          "WHERE `CustomerID` = " + customerWithId.id,
          SpaceRental.createInstanceStatic(),
          pageSize
        ).userSelect(true, "rental", false);

        if (spaceRentalWithId != null) {
          new ViewRentalNode(spaceRentalWithId).runNode();
        } else {
          break;
        }
      } catch (SQLException e) {
        System.out.println("Error! Could not get rentals.");
      }
    }
  }
  
}
