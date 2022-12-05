package cen4333.group2.data.datacontainers;

import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.sqlutilities.Get;
import cen4333.group2.sqlutilities.PrimaryKey;
import cen4333.group2.sqlutilities.QueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.data.datainterfaces.DisplayText;
import cen4333.group2.data.datainterfaces.Duplicate;

public class DataString implements Prototype<DataString>, Duplicate, QueryResult, Get<DataString>, DisplayText, PrimaryKey {

  public String value;
  private String primaryColumn;
  private String displayText;
  private String selectQuery;
  private String countQuery;

  public DataString(String value) {
    this.value = value;
  }

  public DataString(
    String value,
    String primaryColumn,
    String displayText,
    String selectQuery,
    String countQuery
  ) {
    this.value = value;
    this.primaryColumn = primaryColumn;
    this.displayText = displayText;
    this.selectQuery = selectQuery;
    this.countQuery = countQuery;    
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public Duplicate duplicate() {
    return new DataString(value);
  }

  @Override
  public DataString createInstance() {
    return new DataString(null);
  }


  @Override
  public String getPrimaryColumnName() {
    return primaryColumn;
  }

  @Override
  public String getDisplayText() {
    return displayText + value;
  }

  @Override
  public String getSelectFromQuery() {
    return selectQuery;
  }

  @Override
  public String getSelectCountQuery() {
    return countQuery;
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    value = results.getString(2);
  }
  
}
