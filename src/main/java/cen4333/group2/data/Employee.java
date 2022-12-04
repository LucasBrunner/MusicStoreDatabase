package cen4333.group2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.Main;
import cen4333.group2.data.datacontainers.DataWithId;

public class Employee {
  public String checkingNumber;

  public static boolean isPersonEmployee(DataWithId<Person> personWithId) throws SQLException {
    Connection con = Main.globalData.dbConnection.getConnection();
    PreparedStatement query = con.prepareStatement("""
    SELECT `PersonID`
    FROM
      `employee`
      INNER JOIN `person` USING(`PersonID`)
    WHERE `PersonID` = ?;
    """);
    query.setInt(1, personWithId.id);
    ResultSet result = query.executeQuery();
    return result.next();
  }
}
