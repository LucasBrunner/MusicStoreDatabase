package cen4333.group2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cen4333.group2.Main;
import cen4333.group2.daos.sqlutilities.Delete;
import cen4333.group2.daos.sqlutilities.Get;
import cen4333.group2.daos.sqlutilities.Post;
import cen4333.group2.daos.sqlutilities.PrimaryKey;
import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.data.datainterfaces.DisplayText;
import cen4333.group2.data.datainterfaces.Duplicate;

public class Employee implements Prototype<Employee>, QueryResult, Get<Employee>, Duplicate, PrimaryKey, Delete<Employee>, Post<Integer>, DisplayText {
  public String checkingNumber;
  public DataWithId<Person> person;

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

  @Override
  public String getPrimaryColumnName() {
    return "EmployeeID";
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    if (person == null) {
      person = new DataWithId<Person>();
    }
    person.id = results.getInt("PersonID");
    person.data = new Person();
    person.data.fillWithResultSet(results);

    checkingNumber = results.getString("CheckingNumber");
  }

  @SuppressWarnings("unchecked")
  @Override
  public Duplicate duplicate() {
    Employee output = new Employee();
    output.checkingNumber = checkingNumber;
    output.person = (DataWithId<Person>) person.duplicate();
    return output;
  }

  @Override
  public void generatePostSql(Integer personId, List<String> sqlCommands) {

    sqlCommands.add(String.format(
      """
      INSERT INTO `Customer` (
        `PersonID`,
        `CheckingNumber`
      )
      VALUES (
        %d,
        \"%s\"
      );
      """,
      personId,
      checkingNumber
    ));
  }

  public void post(DataWithId<Person> personWithId) {
    post(personWithId.id);
  }

  public void post(Person personData) {
    personData.post(null);
    // This is not multithreading safe. 
    // The LAST_INSERT_ID() MySQL command is session based and currently all sessions use the same connection. 
    post(Main.globalData.dbConnection.getLastId()); 
  }

  @Override
  public String getDeleteSQL() {
    return "DELETE FROM `employee`";
  }

  @Override
  public String getSelectFromQuery() {
    return """
    SELECT
      `EmployeeID`,
      `PersonID`,
      `FirstName`,
      `LastName`,
      `PhoneNumber`,
      `Email`,
      `Address`
    FROM 
      `employee`
      INNER JOIN `person` USING(`PersonID`)
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
      SELECT
        COUNT(`EmployeeID`)
      FROM 
        `employee`
        INNER JOIN `person` USING(`PersonID`)
    """;
  }

  @Override
  public Employee createInstance() {
    return new Employee();
  }

  @Override
  public String getDisplayText() {
    return "Name: " + person.data != null ? person.data.fullName() : "{Error: no data!}";
  }
}
