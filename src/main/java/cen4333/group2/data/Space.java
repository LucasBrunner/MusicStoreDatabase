package cen4333.group2.data;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cen4333.group2.data.datainterfaces.DisplayText;
import cen4333.group2.data.datainterfaces.Duplicate;
import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.sqlutilities.Delete;
import cen4333.group2.sqlutilities.Get;
import cen4333.group2.sqlutilities.Post;
import cen4333.group2.sqlutilities.PrimaryKey;
import cen4333.group2.sqlutilities.QueryResult;

public class Space implements Prototype<Space>, QueryResult, Get<Space>, Duplicate, PrimaryKey, Delete<Space>, Post<Void>, DisplayText {

  public String name;
  public BigDecimal hourlyCost;

  public Space(String name, BigDecimal hourlyCost) {
    this.name = name;
    this.hourlyCost = hourlyCost;
  }

  @Override
  public String getDisplayText() {
    return "Name: " + name;
  }

  @Override
  public void generatePostSql(Void forignData, List<String> sqlCommands) {  
    sqlCommands.add(String.format(
      """
      INSERT INTO `space` (
        Name,
        HourlyCost
      )
      VALUES (
        \"%s\",
        %s
      )
      """,
      name,
      hourlyCost.toString()
    ));
  }

  @Override
  public String getDeleteSQL() {
    return "DELETE FROM `space`";
  }

  @Override
  public String getPrimaryColumnName() {
    return "SpaceID";
  }

  @Override
  public Duplicate duplicate() {
    return new Space(name, hourlyCost);
  }

  @Override
  public String getSelectFromQuery() {
    return """
    SELECT 
      `SpaceID`,
      `Name`,
      `HourlyCost`
    FROM `space`
    """;
  }

  @Override
  public String getSelectCountQuery() {
    return """
    SELECT COUNT(`SpaceID`)
    FROM `space`
    """;
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    name = results.getString("Name");
    hourlyCost = results.getBigDecimal("HourlyCost");
  }

  @Override
  public Space createInstance() {
    return new Space("", new BigDecimal(0));
  }

  public static Space createInstanceStatic() {
    return new Space("", new BigDecimal(0));
  }
  
}
