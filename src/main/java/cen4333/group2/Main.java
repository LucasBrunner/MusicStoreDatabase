package cen4333.group2;

import cen4333.group2.GlobalData;
import cen4333.group2.Node;
import cen4333.group2.Utility;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.nodes.InsertFakeDataNode;
import cen4333.group2.nodes.LoginNode;
import cen4333.group2.nodes.QuitNode;

public class Main {
  public static GlobalData globalData = new GlobalData();

  public static void main(String[] args) {
    while (globalData.doMainLoop) {
      try {
        Utility.printAndGetSelection(new Node[] {
          new LoginNode(),
          new QuitNode(),
          new InsertFakeDataNode()
        }).runNode();

      } catch (NoItemsException e) {}
    }

    System.out.println("\nQuitting program...");
  }
}
