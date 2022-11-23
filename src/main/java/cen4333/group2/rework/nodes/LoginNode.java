package cen4333.group2.rework.nodes;

import cen4333.group2.Main;
import cen4333.group2.journeycliengine.UserInput;
import cen4333.group2.rework.DbConnection;
import cen4333.group2.rework.Node;
import cen4333.group2.rework.errors.DbConnectException;
import cen4333.group2.rework.errors.UnreachableException;
import cen4333.group2.rework.nodes.logintypenodes.ClerkMenu;
import cen4333.group2.rework.nodes.logintypenodes.ManagerMenu;

public class LoginNode extends Node {

  @Override
  public String getName() {
    return "Login";
  }

  @Override
  public void runNode() {
    System.out.print("Enter your username: ");
    String username = UserInput.getString();
    
    System.out.print("Enter your password: ");
    String password = UserInput.getString();

    try {
      Main.globalData.dbConnection = DbConnection.getNew(username, password, "localhost");
      
      switch (Main.globalData.dbConnection.getConnectionType()) {
        case CLERK:
          new ClerkMenu().runNode();
          break;
        case MANAGER:
          new ManagerMenu().runNode();
          break;
      
        default:
          throw new UnreachableException();
      }
    } catch (DbConnectException e) {
      System.out.println("Could not connect!");
    }
  }

  @Override
  public String toString() {
    return "Login";
  }
  
}
