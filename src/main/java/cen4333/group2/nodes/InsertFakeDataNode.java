package cen4333.group2.nodes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.github.javafaker.Faker;

import cen4333.group2.Node;

public class InsertFakeDataNode extends Node {
  
  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/musicstore";

  @Override
  public String getName() {
    return "Insert fake data";
  }
  
  @Override
  public void runNode() {
    try {
      Faker faker = new Faker();

      Class.forName(DRIVER);
      Connection con = DriverManager.getConnection(DATABASE_URL, "java", "java");

      PreparedStatement insertPerson = con.prepareStatement("""
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
      
      String firstName = faker.name().firstName();
      String lastName = faker.name().lastName();
      insertPerson.setString(1, firstName);
      insertPerson.setString(2, lastName);
      insertPerson.setString(3, faker.phoneNumber().phoneNumber());
      insertPerson.setString(4, firstName + "@" + lastName + ".com");
      insertPerson.setString(5, faker.address().fullAddress());
  
      insertPerson.executeUpdate();
  
      PreparedStatement selectID = con.prepareStatement("""
        SELECT MAX(PersonID)
        FROM person;
      """);
      ResultSet results = selectID.executeQuery();
      results.next();
      int personId = results.getInt(1);

      PreparedStatement insertCustomer = con.prepareStatement("""
        INSERT INTO customer (PersonID)
        VALUES (?);
      """);
      insertCustomer.setInt(1, personId);
      insertCustomer.executeUpdate();

    } catch (Exception e) {}
  }
}
