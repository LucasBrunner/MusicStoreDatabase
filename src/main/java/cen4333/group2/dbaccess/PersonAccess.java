package cen4333.group2.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cen4333.group2.data.Person;

public class PersonAccess {
  public static String insertPerson(Connection con, Person person) {
    String output = null;

    try {
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

      insert.setString(1, person.firstName);
      insert.setString(2, person.lastName);
      insert.setString(3, person.phoneNumber);
      insert.setString(4, person.email);
      insert.setString(5, person.address);

      insert.executeUpdate();
    } catch (Exception e) {
      output = "cannot insert person";
    }

    return output;
  }

  public static String updatePerson(Connection con, Person person) {
    String output = null;

    try {
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

      update.setString(1, person.firstName);
      update.setString(2, person.lastName);
      update.setString(3, person.phoneNumber);
      update.setString(4, person.email);
      update.setString(5, person.address);
      update.setInt(5, person.id);

      update.executeUpdate();
    } catch (Exception e) {
      output = "cannot update person";
    }

    return output;
  }

  public static String deletePerson(Connection con, int personID) {
    String output = null;

    try {
      PreparedStatement update = con.prepareStatement("""
        DELETE person
        WHERE person.PersonID = ?;
      """);

      update.setInt(1, personID);

      update.executeUpdate();
    } catch (Exception e) {
      output = "cannot delete person";
    }

    return output;
  }

  public static Person getPerson(Connection con, int personID) {
    Person output = null;

    try {
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

      select.setInt(1, personID);

      ResultSet result = select.executeQuery();
      if (result.next()) {
        output = new Person();
        output.id = personID;
        output.firstName = result.getString(1);
        output.lastName = result.getString(2);
        output.phoneNumber = result.getString(3);
        output.email = result.getString(4);
        output.address = result.getString(5);
      }
    } catch (Exception e) {
      output = null;
    }

    return output;
  }
}
