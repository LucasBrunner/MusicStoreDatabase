package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.DisplayText;
import cen4333.group2.data.datainterfaces.Duplicate;
import cen4333.group2.data.datainterfaces.CreateInstance;

public class Customer implements QueryResult, SelectFrom, CreateInstance, Duplicate, DisplayText {
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
  public CreateInstance createInstance() {
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
      INNER JOIN `person` USING(`PersonID`)\n
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
      SELECT
        COUNT(`CustomerID`)
      FROM 
        `customer`
        INNER JOIN `person` USING(`PersonID`)\n
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
  public String getIdColumnName() {
    return "CustomerID";
  }

  @Override
  public String getDisplayText() {
    return "Name: " + person.data != null ? person.data.fullName() : "{Error: no data!}";
  }
}
