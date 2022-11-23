package cen4333.group2.junctions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cen4333.group2.Enums.UserType;
import cen4333.group2.journeycliengine.GoToPath;
import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;
import cen4333.group2.paths.ForceLogoutPath;

public class UserTypeSelectJunction extends Junction {

  private List<Path> paths;
  private HashSet<UserType> userTypes;

  public UserTypeSelectJunction(HashSet<UserType> userTypes) {
    paths = new ArrayList<Path>();
    this.userTypes = userTypes;

    if (userTypes.contains(UserType.ERROR)) {
      paths.add(new ForceLogoutPath("Invalid user type"));
    } else {
      if (userTypes.contains(UserType.CLERK)) {
        paths.add(new GoToPath(new ClerkJunction()));
      }
      if (userTypes.contains(UserType.MANAGER)) {
        paths.add(new GoToPath(new ManagerJunction()));
      }
    }
    displayMessageOnAutoContinue = false;
  }

  @Override
  public Path[] paths() {
    // TODO Auto-generated method stub
    return paths.toArray(new Path[0]);
  }

  @Override
  public Junction newJunction() {
    return new UserTypeSelectJunction(userTypes);
  }

  @Override
  public String getName() {
    return "Select User Type";
  }
  
}
