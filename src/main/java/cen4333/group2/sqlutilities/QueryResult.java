package cen4333.group2.sqlutilities;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryResult {
  public void fillWithResultSet(ResultSet results) throws SQLException;
}
