package cen4333.group2.sqlutilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Main;
import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datainterfaces.Duplicate;
import cen4333.group2.data.datainterfaces.Prototype;

public interface Put<T extends Put<T> & Duplicate & Prototype<T>> {
  public enum PutResult {
    SUCCESS,
    FAILURE
  }

  public void putSql(List<String> sqlCommands, DataWithId<T> dataWithId);

  default public PutResult put(DataWithId<T> dataWithId) throws SQLException {
    List<String> sqlCommands = new ArrayList<String>();
    putSql(sqlCommands, dataWithId);
    Connection con = Main.globalData.dbConnection.getConnection();
    try {
      con.setAutoCommit(false);

      for (String sqlCommand : sqlCommands) {
        con.prepareStatement(sqlCommand).executeUpdate();
      }

      con.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        con.rollback();
        con.setAutoCommit(true);
      } catch (Exception f) {
        f.printStackTrace();
      }
      return PutResult.FAILURE;
    }
    return PutResult.SUCCESS;
  }
}
