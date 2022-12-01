package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFromWhere;

public class Customer implements QueryResult, SelectFromWhere, Prototype {
  public DataWithId<Person> person;

  @SuppressWarnings("unchecked") // This wouldn't have to be here in Rust.
  @Override
  public Prototype duplicate() {
    Customer customer = new Customer();
    if (person != null) {
      customer.person = (DataWithId<Person>) person.duplicate();
    }
    return customer;
  }

  @Override
  public Prototype duplicateEmpty() {
    return new Customer();
  }

  @Override
  public String getSelectFromWhereQuery(String where) {
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
    """ + where;
  }

  @Override
  public String getSelectCountQuery(String where) {
    return """
      SELECT
        COUNT(`CustomerID`)
      FROM 
        `customer`
        INNER JOIN `person` USING(`PersonID`)\n
    """ + where;
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
}
