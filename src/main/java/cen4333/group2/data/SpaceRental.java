package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.Duplicate;
import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.sqlutilities.Get;
import cen4333.group2.sqlutilities.Post;
import cen4333.group2.sqlutilities.PrimaryKey;
import cen4333.group2.sqlutilities.QueryResult;

public class SpaceRental implements QueryResult, Get<SpaceRental>, Prototype<SpaceRental>, Duplicate, Post<Integer>, PrimaryKey {

  public DataWithId<Space> space;
  public DataWithId<Customer> customer;
  public Timestamp startTimestamp;
  public Timestamp endTimestamp;

  public SpaceRental(DataWithId<Space> space, DataWithId<Customer> customer, Timestamp startTimestamp, Timestamp endTimestamp) {
    this.space = space;
    this.customer = customer;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;  
  }

  @Override
  public String getPrimaryColumnName() {
    return "RentalID";
  }

  @Override
  public void generatePostSql(Integer forignData, List<String> sqlCommands) {
    sqlCommands.add(String.format(
      """
      INSERT INTO `space_rental`
      (
        `SpaceID`,
        `CustomerID`,
        `StartTimestamp`,
        `EndTimestamp`
      )
      VALUES
      (
        %d,
        %d,
        %s,
        %s
      )
      """,
      space.id,
      customer.id,
      startTimestamp.toString(),
      endTimestamp.toString()
    ));
  }

  @SuppressWarnings("unchecked")
  @Override
  public Duplicate duplicate() {
    return new SpaceRental(
      (DataWithId<Space>) space.duplicate(), 
      (DataWithId<Customer>) customer.duplicate(), 
      new Timestamp(startTimestamp.getTime()), 
      new Timestamp(endTimestamp.getTime())
    );
  }

  @Override
  public SpaceRental createInstance() {
    return new SpaceRental(
      new DataWithId<Space>(Space.createInstanceStatic()), 
      new DataWithId<Customer>(Customer.createInstanceStatic()), 
      Timestamp.from(Instant.now()), 
      Timestamp.from(Instant.now())
    );
  }

  @Override
  public String getSelectFromQuery() {
    return """
    SELECT 
      `space_rental`.`RentalID`,
      `space_rental`.`SpaceID`,
      `space_rental`.`CustomerID`,
      `space_rental`.`StartTimestamp`,
      `space_rental`.`EndTimestamp`,
      `space`.`Name`,
      `space`.`HourlyCost`,      
      `customer`.`CustomerID`,
      `customer`.`PersonID`,
      `person`.`FirstName`,
      `person`.`LastName`,
      `person`.`PhoneNumber`,
      `person`.`Email`,
      `person`.`Address`
    FROM 
      `space_rental`
      INNER JOIN `space` USING(`SpaceID`)
      INNER JOIN `customer` USING(`CustomerID`)
      INNER JOIN `person` USING(`PersonID`)
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
    SELECT COUNT(`space_rental`.`RentalID`)
    FROM 
      `space_rental`
      INNER JOIN `space` USING(`SpaceID`)
      INNER JOIN `customer` USING(`CustomerID`)
      INNER JOIN `person` USING(`PersonID`)
    """;
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    space = new DataWithId<Space>(new Space(null, null), results.getInt("SpaceID"));
    space.data.fillWithResultSet(results);
    customer = new DataWithId<Customer>(new Customer(), results.getInt("CustomerID"));
    customer.data.fillWithResultSet(results);

    startTimestamp = results.getTimestamp("StartTimestamp");
    endTimestamp = results.getTimestamp("EndTimestamp");
  }
  
}
