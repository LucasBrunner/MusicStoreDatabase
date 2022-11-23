package cen4333.group2;

import java.util.Arrays;

import cen4333.group2.rework.GlobalData;
import cen4333.group2.rework.Node;
import cen4333.group2.rework.Utility;
import cen4333.group2.rework.errors.NoItemsException;
import cen4333.group2.rework.nodes.LoginNode;
import cen4333.group2.rework.nodes.QuitNode;

public class Main {
  public static GlobalData globalData = new GlobalData();

  public static void main(String[] args) {
    while (globalData.doMainLoop) {
      try {
        Utility.printAndGetSelection(Arrays.asList(new Node[] {
          new LoginNode(),
          new QuitNode()
        })).runNode();

      } catch (NoItemsException e) {}
    }

    System.out.println("\nQuitting program...");
  }
}
