package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFromWhere;

public class PersonData implements QueryResult, SelectFromWhere {
  public String firstName;
  public String lastName;
  public String phoneNumber;
  public String email;
  public String address;

  public Person toPerson(int id) {
    Person output = new Person(id);
    output.data = this;
    return output;
  }

  public String fullName() {
    return firstName + " " + lastName;
  }

  public PersonData fillWithResultSet(ResultSet result) throws SQLException {
    firstName = result.getString("FirstName");
    lastName = result.getString("LastName");
    phoneNumber = result.getString("PhoneNumber");
    phoneNumber = result.wasNull() ? null : phoneNumber;
    email = result.getString("Email");
    email = result.wasNull() ? null : email;
    address = result.getString("Address");
    address = result.wasNull() ? null : address;

    return this;
  }

  public static PersonData fromResultSet(ResultSet result) throws SQLException {
    return new PersonData().fillWithResultSet(result);
  }

  @Override
  public String getSelectFromWhereQuery(String where) {
    return """
      SELECT
        `FirstName`,
        `LastName`,
        `PhoneNumber`,
        `Email`,
        `Address`
      FROM `person`
    """ + where;
  }

  @Override
  public String getSelectCountQuery(String where) {
    return """
      SELECT
        COUNT(`FirstName`)
      FROM `person`
    """ + where;
  }
}
