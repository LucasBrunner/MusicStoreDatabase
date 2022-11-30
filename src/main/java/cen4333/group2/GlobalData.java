package cen4333.group2;

public class GlobalData {
  public enum LoginType {
    NOT_LOGGED_IN,
    CLERK,
    MANAGER
  }

  public boolean doMainLoop = true;
  public DbConnection dbConnection = null;

  public void clear() {
    dbConnection = null;
  }
}
