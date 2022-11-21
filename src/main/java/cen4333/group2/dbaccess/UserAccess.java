package cen4333.group2.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;

public class UserAccess {
  public enum UserType {
    CLERK,
    MANAGER,
    ERROR
  }

  public static HashSet<UserType> getUserType(Connection con, String username, String hostname) {
    HashSet<UserType> output = new HashSet<UserType>();

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
          output.add(UserType.CLERK);
        } else if (grant.contains("`musicstore_manager`@`%`")) {
          output.add(UserType.MANAGER);
        }
        loops += 1;
      }
      
      if (loops == 0) {
        output.add(UserType.ERROR);
      }
    } catch (Exception e) {
      output.add(UserType.ERROR);
    }

    return output;
  }
}
