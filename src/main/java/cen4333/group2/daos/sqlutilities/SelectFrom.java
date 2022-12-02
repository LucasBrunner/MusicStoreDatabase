package cen4333.group2.daos.sqlutilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.Main;

public interface SelectFrom {
  /**
   * 
   * @param where
   * @return a string with a SQL SELECT query for this object. 
   * This query contains all of the data used by this object's QueryResult interface (if there is one) as well as the ID column.
   * The query does not have a semicolon at the end.
   */
  public String getSelectFromQuery(String where);
  public String getSelectCountQuery(String where);
  public String getIdColumnName();

  public static int getCount(SelectFrom object, String where) throws SQLException {
    ResultSet countQueryResult = Main.globalData.dbConnection.getConnection().prepareStatement(object.getSelectCountQuery(where)).executeQuery();
    countQueryResult.next();
    return countQueryResult.getInt(1);
  }
}
