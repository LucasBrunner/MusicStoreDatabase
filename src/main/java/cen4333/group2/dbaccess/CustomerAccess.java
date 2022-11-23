package cen4333.group2.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.data.Customer;
import cen4333.group2.data.Person;

public class CustomerAccess {
  /**
   * Deletes a customer's person data if they are only a customer.
   * If they are also an employee their data is only dereferenced.
   * @param con
   * @param customerID
   * @return null on success.
   */
  public static void deleteCustomer(Connection con, Customer customer) throws SQLException {
    if (isAlsoEmployee(con, customer)) {
      PreparedStatement update = con.prepareStatement("""
        UPDATE person
        SET PersonID = ?
        WHERE person.PersonID = ?;
      """);
      update.setNull(1, java.sql.Types.INTEGER);
      update.setInt(2, customer.getId());
      update.executeUpdate();
    } else {
      PreparedStatement delete = con.prepareStatement("""
        DELETE FROM person
        WHERE person.PersonID = ?;
      """);
      delete.setInt(1, customer.getId());
      delete.executeUpdate();
    }
  }

  public static boolean isAlsoEmployee(Connection con, Customer customer) throws SQLException {
    PreparedStatement check = con.prepareStatement("""
      SELECT 
        employee.EmployeeID
      FROM 
        customer
        LEFT OUTER JOIN employee USING(PersonID)
      WHERE customer.CustomerID = ?;
    """);
    check.setInt(1, customer.getId());
    ResultSet checkResult = check.executeQuery();
    checkResult.next();
    checkResult.getInt(1);
    return !checkResult.wasNull();
  }

  public static Customer createCustomer(Connection con, Person person) throws SQLException {
    Customer output = null;
    int customerId = PersonToCustomerId(con, person);
    if (customerId != -1) {
      output = getCustomer(con, customerId);
    } else {
      try {
        con.setAutoCommit(false);
  
        PreparedStatement update = con.prepareStatement("""
          INSERT INTO customer (PersonID)
          VALUES (?);
        """);
        update.setInt(1, person.getId());
        update.executeUpdate();
  
        PreparedStatement select = con.prepareStatement("""
          SELECT MAX(CustomerID)
          FROM customer;
        """);
        ResultSet result = select.executeQuery();
        result.next();
        output = person.toCustomer(result.getInt(1));
  
        con.setAutoCommit(true);
      } catch (Exception e) {
        con.setAutoCommit(true);
        throw e;
      }
    }

    return output;
  }

  public static int PersonToCustomerId(Connection con, Person person) throws SQLException {
    PreparedStatement select = con.prepareStatement("""
      SELECT CustomerId
      FROM customer
      WHERE PersonID = ?;
    """);
    select.setInt(1, person.getId());
    ResultSet result = select.executeQuery();
    result.next();
    int output = result.getInt(1);
    if (result.wasNull()) {
      output = -1;
    }
    return output;
  }

  public static Customer getCustomer(Connection con, int customerId) throws SQLException {
    PreparedStatement select = con.prepareStatement("""
      SELECT PersonID
      FROM customer;
    """);
    ResultSet result = select.executeQuery();
    result.next();
    Person person = new Person(result.getInt(1));

    PersonAccess.getPerson(con, person);
    return person.toCustomer(customerId);
  }
}
