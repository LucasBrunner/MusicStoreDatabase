package cen4333.group2.daos.sqlutilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.Main;

public interface Post<T> {
  public enum PostResult {
    SUCCESS,
    FAILURE
  }

  public void generatePostSql(T forignData, List<String> sqlCommands);

  public default PostResult post(T forignData) {
    List<String> sqlCommands = new ArrayList<String>();
    generatePostSql(forignData, sqlCommands);
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
      return PostResult.FAILURE;
    }
    return PostResult.SUCCESS;
  }
}
