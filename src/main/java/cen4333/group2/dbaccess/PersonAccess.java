package cen4333.group2.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;

import cen4333.group2.Enums.Result;
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
}
