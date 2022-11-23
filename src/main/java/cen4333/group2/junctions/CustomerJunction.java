package cen4333.group2.junctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cen4333.group2.Enums.UserType;
import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;
import cen4333.group2.paths.customerpaths.AddCustomerPath;
import cen4333.group2.paths.customerpaths.RemoveCustomerDataPath;
import cen4333.group2.paths.customerpaths.SearchCustomersPath;
import cen4333.group2.paths.customerpaths.ViewCustomersPath;

public class CustomerJunction extends Junction {
  
  private UserType userType;
  private List<Path> paths = new ArrayList<Path>(Arrays.asList(new Path[] {
    new AddCustomerPath(),
    new ViewCustomersPath(),
    new SearchCustomersPath()
  }));
  
  public CustomerJunction(UserType userType) {
    this.userType = userType;
    if (userType == UserType.MANAGER) {
      paths.add(new RemoveCustomerDataPath());
    }
  }

  @Override
  public Path[] paths() {
    return paths.toArray(new Path[0]);
  }

  @Override
  public Junction newJunction() {
    return new CustomerJunction(userType);
  }

  @Override
  public String getName() {
    return "Customer Menu";
  }
}
