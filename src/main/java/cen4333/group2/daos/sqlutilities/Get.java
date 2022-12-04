package cen4333.group2.daos.sqlutilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Main;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public interface Get<T extends CreateInstance & Duplicate & QueryResult & Get<T>> {
  /**
   * 
   * @return a string with a SQL SELECT query for this object. 
   * This query contains all of the data used by this object's QueryResult interface (if there is one) as well as the ID column.
   * The query does not have a semicolon at the end.
   */
  public String getSelectFromQuery();
  public String getSelectCountQuery();
  public String getIdColumnName();

  default public void getChildren(DataWithId<T> self) {};

  default public int getCount(String where) throws SQLException {
    ResultSet countQueryResult = Main.globalData.dbConnection.getConnection().prepareStatement(this.getSelectCountQuery() + " \n " + where).executeQuery();
    countQueryResult.next();
    return countQueryResult.getInt(1);
  }

  default public ResultSet getSelectFrom(String where) throws SQLException {
    return Main.globalData.dbConnection.getConnection().prepareStatement(this.getSelectFromQuery() + " \n " + where).executeQuery();
  }

  @SuppressWarnings("unchecked")
  default public List<DataWithId<T>> getList(String where, T prototype) throws SQLException {
    ResultSet results = getSelectFrom(where);
    List<DataWithId<T>> output = new ArrayList<DataWithId<T>>();
    while (results.next()) {
      T data = (T) prototype.createInstance();
      data.fillWithResultSet(results);
      DataWithId<T> newItem = new DataWithId<T>(
        data,
        results.getInt(getIdColumnName())
      );
      newItem.data.getChildren(newItem);
      output.add(newItem);
    }
    return output;
  }
}
