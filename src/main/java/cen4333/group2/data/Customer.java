package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.Main;
import cen4333.group2.daos.sqlutilities.Get;
import cen4333.group2.daos.sqlutilities.Post;
import cen4333.group2.daos.sqlutilities.PrimaryKey;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.DisplayText;
import cen4333.group2.data.datainterfaces.Duplicate;
import cen4333.group2.data.datainterfaces.Prototype;

public class Customer implements QueryResult, Get<Customer>, Prototype<Customer>, Duplicate, DisplayText, PrimaryKey, Post<Integer> {
  public DataWithId<Person> person;

  @SuppressWarnings("unchecked") // This wouldn't have to be here in Rust.
  @Override
  public Duplicate duplicate() {
    Customer customer = new Customer();
    if (person != null) {
      customer.person = (DataWithId<Person>) person.duplicate();
    }
    return customer;
  }

  @Override
  public Customer createInstance() {
    return new Customer();
  }

  @Override
  public String getSelectFromQuery() {
    return """
    SELECT
      `CustomerID`,
      `PersonID`,
      `FirstName`,
      `LastName`,
      `PhoneNumber`,
      `Email`,
      `Address`
    FROM 
      `customer`
      INNER JOIN `person` USING(`PersonID`)
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
      SELECT
        COUNT(`CustomerID`)
      FROM 
        `customer`
        INNER JOIN `person` USING(`PersonID`)
    """;
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    person = new DataWithId<Person>();
    person.id = results.getInt("PersonID");
    Person personData = new Person();
    personData.fillWithResultSet(results);
    person.data = personData;
  }

  @Override
  public String getDisplayText() {
    return "Name: " + person.data != null ? person.data.fullName() : "{Error: no data!}";
  }

  @Override
  public String getPrimaryColumnName() {
    return "CustomerID";
  }

  @Override
  public void generatePostSql(Integer personId, List<String> sqlCommands) {
    sqlCommands.add(String.format(
      """
      INSERT INTO `Customer` (
        `PersonID`
      )
      VALUES (
        %s
      );
      """,
      personId
    ));
  }

  public static void post(DataWithId<Person> personWithId) {
    new Customer().post(personWithId.id);
  }

  public static void post(Person personData) {
    personData.post(null);
    // This is not multithreading safe. 
    // The LAST_INSERT_ID() MySQL command is session based and currently all sessions use the same connection. 
    new Customer().post(Main.globalData.dbConnection.getLastId()); 
  }
}
