package cen4333.group2.daos.sqlutilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Main;
import cen4333.group2.data.DataWithId;
import cen4333.group2.data.Prototype;

public class SelectFromWhereIter <T extends QueryResult & SelectFromWhere & Prototype> {
  private String where;
  private T prototype;
  private int stepSize;

  private int currentPosition = 0;
  private int rowCount;

  public SelectFromWhereIter(String where, T prototype, int stepSize) throws SQLException {
    this.where = where;
    this.prototype = prototype;
    this.stepSize = stepSize;

    // System.out.println(prototype.getSelectCountQuery(where));
    ResultSet countQueryResult = Main.globalData.dbConnection.getConnection().prepareStatement(prototype.getSelectCountQuery(where)).executeQuery();
    countQueryResult.next();
    this.rowCount = countQueryResult.getInt(1);
  }

  @SuppressWarnings("unchecked") // This wouldn't have to be here in Rust.
  /**
   * Gets the current page.
   * @return 
   */
  public List<DataWithId<T>> getPage() throws SQLException, ClassCastException {
    String query = prototype.getSelectFromWhereQuery(where) + String.format(
      """
        \nLIMIT %d
        OFFSET %d
      """, 
      stepSize + 1,
      currentPosition * stepSize
    );
    ResultSet results = Main.globalData.dbConnection.getConnection().prepareStatement(query).executeQuery();
    
    List<DataWithId<T>> output = new ArrayList<DataWithId<T>>();
    while (results.next()) {
      DataWithId<T> nextItem = new DataWithId<T>();
      nextItem.data = (T) prototype.duplicateEmpty();
      nextItem.data.fillWithResultSet(results);
      nextItem.id = results.getInt(prototype.getIdColumnName());
      output.add(nextItem);
    }
    while (output.size() > stepSize) {
      output.remove(output.size() - 1);
    }
    return output;
  }

  /**
   * Moves to the next page.
   * @return true if there was a page to move to.
   */
  public void nextPage() {
    if (hasNextPage()) {
      currentPosition += 1;
    }
  }

  /**
   * Moves to the previous page.
   * @return true if there was a page to move to.
   */
  public void previousPage() {
    if (currentPosition > 0) {
      currentPosition -= 1;
    } 
  }

  public boolean hasNextPage() {
    return (rowCount / stepSize) > currentPosition;
  }

  public boolean hasPreviousPage() {
    return currentPosition > 0;
  }

  public int getOffset() {
    return currentPosition * stepSize;
  }

  public int getStepSize() {
    return stepSize;
  }
}
