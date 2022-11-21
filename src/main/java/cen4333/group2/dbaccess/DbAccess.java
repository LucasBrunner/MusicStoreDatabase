package cen4333.group2.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbAccess {
  
  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/musicstore";
  
  /**
   * 
   * @param username
   * @param password
   * @return A Connection object if the connection was successful, null if it failed.
   */
  public static Connection getConnection(String username, String password) {
    Connection output = null;

    try {
      Class.forName(DRIVER);
      output = DriverManager.getConnection(DATABASE_URL, username, password);
    } catch (Exception e) {
      // e.printStackTrace();
      // System.out.println(e.getLocalizedMessage());
    }

    return output;
  }
}
