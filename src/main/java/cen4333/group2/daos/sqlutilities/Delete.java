package cen4333.group2.daos.sqlutilities;

import java.sql.SQLException;
import java.util.List;

import cen4333.group2.Main;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public interface Delete<T extends CreateInstance & Duplicate & PrimaryKey> {
  public String getDeleteSQL();

  default public void delete(DataWithId<T> id, List<String> whereClauses) throws SQLException {
    StringBuilder sqlStatement = new StringBuilder(getDeleteSQL());
    sqlStatement.append("\nWHERE");
    sqlStatement.append("\n`" + id.data.getPrimaryColumnName() +"` = " + id.id);
    if (whereClauses.size() > 0) {
      for (String whereClause : whereClauses) {
        sqlStatement.append(whereClause);
      }
    }
    Main.globalData.dbConnection.getConnection().prepareStatement(sqlStatement.toString()).executeUpdate();
  }
}
