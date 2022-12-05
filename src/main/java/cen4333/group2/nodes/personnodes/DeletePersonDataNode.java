package cen4333.group2.nodes.personnodes;

import java.sql.SQLException;
import java.util.ArrayList;

import cen4333.group2.Main;
import cen4333.group2.Node;
import cen4333.group2.data.Employee;
import cen4333.group2.data.Person;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.utility.UserInput;
import cen4333.group2.utility.UserInput.YesNo;

public class DeletePersonDataNode extends Node {

  private DataWithId<Person> personWithId;

  public DeletePersonDataNode(DataWithId<Person> person) {
    this.personWithId = person;
  }

  @Override
  public String getName() {
    return "Delete person data";
  }

  @Override
  public void runNode() {
    System.out.println("Are you sure you want to delete this person's data?");
    if (UserInput.getYesNo() == YesNo.YES) {
      try {
        if (!Employee.isPersonEmployee(personWithId) || Main.globalData.dbConnection.getConnectionType().canDeleteEmployeeData()) {
          personWithId.data.delete(personWithId, new ArrayList<String>());
        } else {
          System.out.println("You do not have the authorization to delete the data for customers who are also employees.");
        }
      } catch (SQLException e) {
        System.out.println("Error! could not delete the person's data.");
      }
    }
  }
  
}
