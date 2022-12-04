package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.sqlutilities.QueryResult;
import cen4333.group2.sqlutilities.Delete;
import cen4333.group2.sqlutilities.Get;
import cen4333.group2.sqlutilities.Post;
import cen4333.group2.sqlutilities.PrimaryKey;
import cen4333.group2.sqlutilities.Put;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datacontainers.ObjectWithValue;
import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.data.datainterfaces.Duplicate;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.utility.DelayedUserInputString;
import cen4333.group2.utility.ObjectSelector;
import cen4333.group2.utility.DelayedUserInputString.UserInputType;

public class Person implements QueryResult, Get<Person>, Prototype<Person>, Duplicate, PrimaryKey, Delete<Person>, Post<Void>, Put<Person> {
  private static final List<ObjectWithValue<String, DelayedUserInputString>> PERSON_SEARCH_METHODS = new ArrayList<ObjectWithValue<String, DelayedUserInputString>>();

  static {
    PERSON_SEARCH_METHODS.add(new ObjectWithValue<String, DelayedUserInputString>(
      "Name", 
      new DelayedUserInputString(
        "WHERE CONCAT(`FirstName`, \" \", `LastName`) LIKE \"%%%s%%\"", 
        "Enter the name to search: ",
        UserInputType.STRING
      )
    ));
    PERSON_SEARCH_METHODS.add(new ObjectWithValue<String, DelayedUserInputString>(
      "Address", 
      new DelayedUserInputString(
        "WHERE `Address` LIKE \"%%%s%%\"", 
        "Enter the address to search: ",
        UserInputType.STRING
      )
    ));
    PERSON_SEARCH_METHODS.add(new ObjectWithValue<String, DelayedUserInputString>(
      "Email", 
      new DelayedUserInputString(
        "WHERE `Email` LIKE \"%%%s%%\"", 
        "Enter the email to search: ",
        UserInputType.STRING
      )
    ));
    PERSON_SEARCH_METHODS.add(new ObjectWithValue<String, DelayedUserInputString>(
      "Phone number", 
      new DelayedUserInputString(
        "WHERE `PhoneNumber` LIKE \"%%%s%%\"", 
        "Enter the phone number to search: ",
        UserInputType.STRING
      )
    ));
  }
  
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
  public Duplicate duplicate() {
    Person person = new Person();
    person.firstName = firstName;
    person.lastName = lastName;
    person.phoneNumber = phoneNumber;
    person.email = email;
    person.address = address;
    return person;
  }

  @Override
  public Person createInstance() {
    return new Person();
  }

  @Override
  public String getDeleteSQL() {
    return """
    DELETE FROM `person`
    """;
  }

  @Override
  public String getPrimaryColumnName() {
    return "PersonID";
  }

  @Override
  public void generatePostSql(Void forignData, List<String> sqlCommands) {
    sqlCommands.add(String.format(
      """
        INSERT INTO `musicstore`.`person`
        (
          `FirstName`,
          `LastName`,
          `PhoneNumber`,
          `Email`,
          `Address`
        )
        VALUES
        (
          \"%s\",
          \"%s\",
          \"%s\",
          \"%s\",
          \"%s\"
        );
         
      """, 
      firstName, 
      lastName,
      phoneNumber,
      email,
      address
    ));
  }

  public static String personSearchString(List<ObjectWithValue<String, DelayedUserInputString>> additionOptions) {
    System.out.println("What would you like to search by?");
    List<ObjectWithValue<String, DelayedUserInputString>> options = new ArrayList<ObjectWithValue<String, DelayedUserInputString>>(); 
    options.addAll(PERSON_SEARCH_METHODS);
    options.addAll(additionOptions);
    try {
      return ObjectSelector.printAndGetSelection(options).value.get();
    } catch (NoItemsException e) {}
    return null;
  }

  @Override
  public void putSql(List<String> sqlCommands, DataWithId<Person> dataWithId) {
    sqlCommands.add(String.format(
      """
      UPDATE `person`
      SET
        `FirstName` = \"%s\",
        `LastName` = \"%s\",
        `PhoneNumber` = \"%s\",
        `Email` = \"%s\",
        `Address` = \"%s\"
      WHERE `PersonID` = %d
      """,
      dataWithId.data.firstName,
      dataWithId.data.lastName,
      dataWithId.data.phoneNumber,
      dataWithId.data.email,
      dataWithId.data.address,
      dataWithId.id
    ));
  }
}
