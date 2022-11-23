package cen4333.group2.rework;

import java.util.List;

import cen4333.group2.journeycliengine.UserInput;
import cen4333.group2.rework.errors.NoNodesException;

public class Utility {
  public static Node printAndGetSelection(List<Node> goToViews) throws NoNodesException {
    if (goToViews == null || goToViews.size() <= 0) {
      throw new NoNodesException();
    }

    for (int i = 0; i < goToViews.size(); i++) {
      System.out.printf("%d: %s\n", i + 1, goToViews.get(i).getName());
    }

    System.out.print("\nPlease enter the number corresponding to the option you wish to select: ");
    int selected = 0;
    while (true) {
      selected = UserInput.getInt();
      if (selected < 1 || selected > goToViews.size()) {
        System.out.print("Please enter a valid option: ");
      } else {
        break;
      }
    }
    return goToViews.get(selected - 1);
  }
}
