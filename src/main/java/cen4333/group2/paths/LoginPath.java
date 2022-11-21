package cen4333.group2.paths;

import java.sql.Connection;
import java.util.HashSet;

import cen4333.group2.dbaccess.DbAccess;
import cen4333.group2.dbaccess.UserAccess;
import cen4333.group2.dbaccess.UserAccess.UserType;
import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;
import cen4333.group2.journeycliengine.UserInput;
import cen4333.group2.junctions.HomeJunction;
import cen4333.group2.junctions.UserTypeSelectJunction;

public class LoginPath extends Path {

  private Junction nextJunction = null;

  @Override
  public String name() {
    return "Login";
  }

  @Override
  public Junction junction() {
    return nextJunction == null ? new HomeJunction() : nextJunction;
  }

  @Override
  public void run() {
    System.out.println("Enter your username: ");
    String username = UserInput.getString();
    
    System.out.println("Enter your password: ");
    String password = UserInput.getString();

    Connection con = DbAccess.getConnection(username, password);
    if (con != null) {
      HashSet<UserType> userTypes = UserAccess.getUserType(con, username, "localhost");
      nextJunction = new UserTypeSelectJunction(userTypes);
    } else {
      System.out.println("Invalid login.");
      nextJunction = new HomeJunction();
    }
  }
  
}
