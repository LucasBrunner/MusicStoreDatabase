package cen4333.group2.nodes;

import cen4333.group2.Main;
import cen4333.group2.UserInput;
import cen4333.group2.DbConnection;
import cen4333.group2.Node;
import cen4333.group2.errors.DbConnectException;
import cen4333.group2.errors.UnreachableException;
import cen4333.group2.nodes.logintypenodes.ClerkMenu;
import cen4333.group2.nodes.logintypenodes.ManagerMenu;

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
