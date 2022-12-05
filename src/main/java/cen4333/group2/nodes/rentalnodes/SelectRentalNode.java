package cen4333.group2.nodes.rentalnodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Node;
import cen4333.group2.sqlutilities.GetIter;
import cen4333.group2.data.Person;
import cen4333.group2.data.SpaceRental;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datacontainers.ObjectWithValue;
import cen4333.group2.utility.DelayedUserInputString;
import cen4333.group2.utility.DelayedUserInputString.UserInputType;

public class SelectRentalNode extends Node {
  @Override
  public String getName() {
    return "Search rentals";
  }

  public static List<ObjectWithValue<String, DelayedUserInputString>> additionalOptions() {
    List<ObjectWithValue<String, DelayedUserInputString>> additionalOptions = new ArrayList<ObjectWithValue<String, DelayedUserInputString>>();
    additionalOptions.add(new ObjectWithValue<String, DelayedUserInputString>(
      "Customer ID", 
      new DelayedUserInputString(
        "WHERE `CustomerID` = %d", 
        "Enter the id to search: ",
        UserInputType.INTEGER
      )
    ));
    additionalOptions.add(new ObjectWithValue<String, DelayedUserInputString>(
      "Space", 
      new DelayedUserInputString(
        "WHERE `Name` = \"%s\"", 
        "Enter the name to search: ",
        UserInputType.STRING
      )
    ));
    additionalOptions.add(new ObjectWithValue<String, DelayedUserInputString>(
      "Date", 
      new DelayedUserInputString(
        "WHERE DAY(`StartTimestamp`) = DAY(%1s) AND DAY(`EndTimestamp`) = DAY(%1s)",
        "Enter the date to search.\n",
        UserInputType.DATE_AS_SQL_STRING
      )
    ));
    return additionalOptions;
  }

  @Override
  public void runNode() {
    System.out.println("What would you like to search by?");
    searchRental(Person.personSearchString(additionalOptions()));
  }

  private void searchRental(String where) {
    try {
      DataWithId<SpaceRental> selectedRental = new GetIter<SpaceRental>(
        where, 
        SpaceRental.createInstanceStatic(), 
        GetIter.getResultsAmount()
      ).userSelect(true, "customer", true);
      if (selectedRental != null) {
        new ViewRentalNode(selectedRental).runNode();
      } else {

      }
    } catch (SQLException e) {
      System.out.println("There was a database error!");
      e.printStackTrace();
    }
  }
  
}
