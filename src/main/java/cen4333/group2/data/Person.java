package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;

public class Person implements QueryResult, SelectFrom, Prototype {
  public String firstName;
  public String lastName;
  public String phoneNumber;
  public String email;
  public String address;

  public String fullName() {
    return firstName + " " + lastName;
  }

  public void fillWithResultSet(ResultSet result) throws SQLException {
    firstName = result.getString("FirstName");
    lastName = result.getString("LastName");
    phoneNumber = result.getString("PhoneNumber");
    phoneNumber = result.wasNull() ? null : phoneNumber;
    email = result.getString("Email");
    email = result.wasNull() ? null : email;
    address = result.getString("Address");
    address = result.wasNull() ? null : address;
  }

  @Override
  public String getSelectFromQuery() {
    return """
      SELECT
        `PersonID`,
        `FirstName`,
        `LastName`,
        `PhoneNumber`,
        `Email`,
        `Address`
      FROM `person`\n
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
      SELECT
        COUNT(`PersonID`)
      FROM `person`\n
    """;
  }

  @Override
  public Prototype duplicate() {
    Person person = new Person();
    person.firstName = firstName;
    person.lastName = lastName;
    person.phoneNumber = phoneNumber;
    person.email = email;
    person.address = address;
    return person;
  }

  @Override
  public Prototype duplicateEmpty() {
    return new Person();
  }

  @Override
  public String getIdColumnName() {
    return "PersonID";
  }
}
