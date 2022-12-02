package cen4333.group2.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cen4333.group2.Main;
import cen4333.group2.Enums.Result;
import cen4333.group2.data.Customer;
import cen4333.group2.data.Person;
import cen4333.group2.data.datacontainers.DataWithId;

public class CustomerDao {
  /**
   * 
   * @param name
   * @param lastName
   * @param amount
   * @param offset
   * @param output The results of the query are placed in this list.
   * @return true if there were more items in the query than fit in the return amount specified.
   * @throws SQLException
   */
  public static boolean searchCustomersByName(String name, int amount, int offset, List<DataWithId<Customer>> output) throws SQLException {
    return searchCustomers(String.format("WHERE CONCAT(FirstName, \" \", LastName) LIKE \"%%%s%%\"", name), amount, offset, output);
  }
  /**
   * 
   * @param name
   * @param lastName
   * @param amount
   * @param offset
   * @param output The results of the query are placed in this list.
   * @return true if there were more items in the query than fit in the return amount specified.
   * @throws SQLException
   */
  public static boolean searchCustomersById(int customerId, int amount, int offset, List<DataWithId<Customer>> output) throws SQLException {
    return searchCustomers("WHERE CustomerID = " + customerId, amount, offset, output);
  }

  private static boolean searchCustomers(String whereClause, int amount, int offset, List<DataWithId<Customer>> output) throws SQLException {
    output.clear();
    boolean outputBool = false;

    Connection con = Main.globalData.dbConnection.getConnection();
    PreparedStatement query = con.prepareStatement(String.format(
      """
      SELECT 
        PersonID,
        CustomerID,
        FirstName,
        LastName,
        PhoneNumber,
        Email,
        Address
      FROM
        person
        INNER JOIN customer USING(PersonID)
      %s
      LIMIT ? 
      OFFSET ?;
    """, 
    whereClause));
    
    query.setInt(1, amount + 1);
    query.setInt(2, offset);


    ResultSet results = query.executeQuery();

    while (results.next()) {
      Person person = new Person();
      person.firstName   = results.getString(3);
      person.lastName    = results.getString(4);
      person.phoneNumber = results.getString(5);
      person.email       = results.getString(6);
      person.address     = results.getString(7);

      DataWithId<Person> personWithId = new DataWithId<Person>();
      personWithId.data = person;
      personWithId.id = results.getInt(1);
    
      DataWithId<Customer> customer = new DataWithId<Customer>();
      customer.data = new Customer();
      customer.data.person = personWithId;
      customer.id = results.getInt(2);
      output.add(customer);
    }

    while (output.size() > amount) {
      output.remove(output.size() - 1);
      outputBool = true;
    }

    return outputBool;
  }

  public static Result updateCustomer(DataWithId<Customer> customer) throws SQLException {
    Connection con = Main.globalData.dbConnection.getConnection();
    
    PreparedStatement update = con.prepareStatement("""
      UPDATE 
        `person`
        INNER JOIN `customer` USING (PersonID)
      SET
        `FirstName` = ?,
        `LastName` = ?,
        `PhoneNumber` = ?,
        `Email` = ?,
        `Address` = ?
      WHERE CustomerID = ?;
    """);

    Person person = customer.data.person.data;
    update.setString(1, person.firstName);
    update.setString(2, person.lastName);
    update.setString(3, person.phoneNumber);
    update.setString(4, person.email);
    update.setString(5, person.address);
    
    update.setInt(6, customer.id);

    update.executeUpdate();

    return Result.Ok;
  }
}
