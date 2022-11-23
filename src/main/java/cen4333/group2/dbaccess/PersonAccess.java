package cen4333.group2.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.Enums.Result;
import cen4333.group2.rework.data.Person;
import cen4333.group2.rework.data.PersonData;

public class PersonAccess {
  /**
   * The person object will be updated with the new person's id.
   * @param con
   * @param data
   * @throws SQLException
   */
  public static Person insertPerson(Connection con, PersonData data) throws SQLException {
    Person output = null;
    try {
      con.setAutoCommit(false);

      PreparedStatement insert = con.prepareStatement("""
        INSERT INTO person (
          FirstName,
          LastName,
          PhoneNumber,
          Email,
          Address
        )
        VALUES (
          ?,
          ?,
          ?,
          ?,
          ?
        );
      """);
  
      insert.setString(1, data.firstName);
      insert.setString(2, data.lastName);
      insert.setString(3, data.phoneNumber);
      insert.setString(4, data.email);
      insert.setString(5, data.address);
  
      insert.executeUpdate();
  
      PreparedStatement selectID = con.prepareStatement("""
        SELECT MAX(PersonID)
        FROM person;
      """);
      ResultSet results = selectID.executeQuery();
      results.next();
      output =  data.toPerson(results.getInt(1));
      
      con.setAutoCommit(true);
      
    } catch (Exception e) {
      con.setAutoCommit(true);
      throw e;
    }        

    return output;
  }

  public static void updatePerson(Connection con, Person person) throws SQLException {
    PreparedStatement update = con.prepareStatement("""
      UPDATE person
      SET
        FirstName = ?,
        LastName = ?,
        PhoneNumber = ?,
        Email = ?,
        Address = ?
      WHERE person.PersonID = ?;
    """);

    PersonData data = person.data;
    update.setString(1, data.firstName);
    update.setString(2, data.lastName);
    update.setString(3, data.phoneNumber);
    update.setString(4, data.email);
    update.setString(5, data.address);
    update.setInt(5, person.getId());

    update.executeUpdate();
  }

  public static Result deletePerson(Connection con, int personID) throws SQLException {
    PreparedStatement delete = con.prepareStatement("""
      DELETE FROM person
      WHERE person.PersonID = ?;
    """);

    delete.setInt(1, personID);

    if (delete.executeUpdate() > 0) {
      return Result.Ok;
    } else {
      return Result.Error;
    }
  }

  /**
   * Updates the data of the Person in place.
   * @param con
   * @param person
   * @throws SQLException
   */
  public static void getPerson(Connection con, Person person) throws SQLException {
    PreparedStatement select = con.prepareStatement("""
      SELECT 
        FirstName,
        LastName,
        PhoneNumber,
        Email,
        Address
      FROM person
      WHERE PersonID = ?;
    """);

    select.setInt(1, person.getId());

    ResultSet result = select.executeQuery();
    if (result.next()) {
      PersonData data = new PersonData();
      data.firstName = result.getString(1);
      data.lastName = result.getString(2);
      data.phoneNumber = result.getString(3);
      data.email = result.getString(4);
      data.address = result.getString(5);
    }
  }

  public static boolean doesPersonExist(Connection con, Person person) throws SQLException {
    PreparedStatement select = con.prepareStatement("""
      SELECT PersonID
      FROM person
      WHERE PersonID = ?;
    """);

    select.setInt(1, person.getId());
    
    ResultSet result = select.executeQuery();
    return result.next();
  }
}
