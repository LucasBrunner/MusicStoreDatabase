package cen4333.group2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;

import cen4333.group2.utility.Utility;
import cen4333.group2.errors.AccessNotAllowedException;
import cen4333.group2.errors.DbConnectException;
import cen4333.group2.errors.NoItemsException;

public class DbConnection {
  
  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/musicstore";

  public enum ConnectionType {
    CLERK,
    MANAGER;

    @Override
    public String toString() {
      switch (this) {
        case CLERK:
          return "Clerk";
        case MANAGER:
          return "Manager";
        default:
          return "";

      }
    }

    public boolean showWholesalePrices() {
      switch (this) {
        case MANAGER:
          return true;
        default:
          return false;
      }
    }
  };

  private Connection con;
  private ConnectionType conType = null;

  private DbConnection() {};
  
  /**
   * @param username
   * @param password
   * @return A DbConnection with a valid connection to the database.
   * @throws DbConnectException
   */
  public static DbConnection getNew(String username, String password) throws DbConnectException {
    return DbConnection.getNew(username, password, "%");
  }
  
  /**
   * 
   * @param username
   * @param password
   * @param hostname
   * @return A DbConnection with a valid connection to the database.
   * @throws DbConnectException
   */
  public static DbConnection getNew(String username, String password, String hostname) throws DbConnectException {
    DbConnection output = new DbConnection();

    try {
      Class.forName(DRIVER);
      output.con = DriverManager.getConnection(DATABASE_URL, username, password);
      HashSet<ConnectionType> connectionTypes = DbConnection.findConnectionType(output.getConnection(), username, hostname);
      if (connectionTypes.size() == 1) {
        output.conType = connectionTypes.toArray(new ConnectionType[0])[0];
      } else {
        System.out.println("\nWhat login type would you like to use?");
        output.conType = selectConnectionType(connectionTypes);
      }
    } catch (Exception e) {
      throw new DbConnectException();
    }

    if (output.con == null) {
      throw new DbConnectException();
    }
    return output;
  }

  private static ConnectionType selectConnectionType(HashSet<ConnectionType> connectionTypes) throws NoItemsException {
    return Utility.printAndGetSelection(connectionTypes.toArray(new ConnectionType[0]));
  }

  private static HashSet<ConnectionType> findConnectionType(Connection con, String username, String hostname) throws AccessNotAllowedException {
    HashSet<ConnectionType> output = new HashSet<ConnectionType>();
    
    try {
      PreparedStatement statement = con.prepareStatement(String.format(
        "SHOW GRANTS FOR `%s`@`%s`;",
        username,
        hostname
      ));
      ResultSet results = statement.executeQuery();
      
      int loops = 0;
      while (results.next()) {
        String grant = results.getString(1);
        if (grant.contains("`musicstore_clerk`@`%`")) {
          output.add(ConnectionType.CLERK);
        } 
        if (grant.contains("`musicstore_manager`@`%`")) {
          output.add(ConnectionType.MANAGER);
        }
        loops += 1;
      }
      
      if (loops == 0) {
        throw new AccessNotAllowedException();
      }
    } catch (Exception e) {
      throw new AccessNotAllowedException();
    }

    return output;
  }

  public Connection getConnection() {
    return con;
  }

  public ConnectionType getConnectionType() {
    return conType;
  }
}
