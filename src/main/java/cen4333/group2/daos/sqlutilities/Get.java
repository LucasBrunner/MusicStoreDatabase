package cen4333.group2.daos.sqlutilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.Main;

public interface Get {
  /**
   * 
   * @return a string with a SQL SELECT query for this object. 
   * This query contains all of the data used by this object's QueryResult interface (if there is one) as well as the ID column.
   * The query does not have a semicolon at the end.
   */
  public String getSelectFromQuery();
  public String getSelectCountQuery();
  public String getIdColumnName();

  default public int getCount(String where) throws SQLException {
    ResultSet countQueryResult = Main.globalData.dbConnection.getConnection().prepareStatement(this.getSelectCountQuery() + " \n " + where).executeQuery();
    countQueryResult.next();
    return countQueryResult.getInt(1);
  }

  default public ResultSet getSelectFrom(String where) throws SQLException {
    return Main.globalData.dbConnection.getConnection().prepareStatement(this.getSelectFromQuery() + " \n " + where).executeQuery();
  }
}
