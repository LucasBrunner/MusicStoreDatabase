package cen4333.group2;

import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.LoginNode;
import cen4333.group2.nodes.QuitNode;
import cen4333.group2.utility.ObjectSelector;

public class Main {
  public static GlobalData globalData = new GlobalData();

  public static void main(String[] args) {
    while (globalData.doMainLoop) {
      try {
        ObjectSelector.printAndGetSelection(new Node[] {
          new LoginNode(),
          new QuitNode()
        }).runNode();

      } catch (NoItemsException e) {}
    }

    System.out.println("\nQuitting program...");
  }
}
