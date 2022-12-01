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
import cen4333.group2.data.PersonData;

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
  public static boolean searchCustomersByName(String name, int amount, int offset, List<Customer> output) throws SQLException {
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
  public static boolean searchCustomersById(int customerId, int amount, int offset, List<Customer> output) throws SQLException {
    return searchCustomers("WHERE CustomerID = " + customerId, amount, offset, output);
  }

  private static boolean searchCustomers(String whereClause, int amount, int offset, List<Customer> output) throws SQLException {
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
      PersonData pd = new PersonData();
      pd.firstName   = results.getString(3);
      pd.lastName    = results.getString(4);
      pd.phoneNumber = results.getString(5);
      pd.email       = results.getString(6);
      pd.address     = results.getString(7);

      Person p = pd.toPerson(results.getInt(1));
    
      Customer c = p.toCustomer(results.getInt(2));
      output.add(c);
    }

    while (output.size() > amount) {
      output.remove(output.size() - 1);
      outputBool = true;
    }

    return outputBool;
  }

  public static Result updateCustomer(Customer customer) throws SQLException {
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

    PersonData p = customer.getPersonData();
    update.setString(1, p.firstName);
    update.setString(2, p.lastName);
    update.setString(3, p.phoneNumber);
    update.setString(4, p.email);
    update.setString(5, p.address);
    
    update.setInt(6, customer.getId());

    update.executeUpdate();

    return Result.Ok;
  }
}
