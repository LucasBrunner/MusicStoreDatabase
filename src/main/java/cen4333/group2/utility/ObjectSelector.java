package cen4333.group2.utility;

import java.util.List;

import cen4333.group2.errors.NoItemsException;

public class ObjectSelector {

  public static <T> T printAndGetSelection(T[] items) throws NoItemsException {
    if (items == null || items.length <= 0) {
      throw new NoItemsException();
    }

    for (int i = 0; i < items.length; i++) {
      System.out.printf("%d: %s\n", i + 1, items[i].toString());
    }

    System.out.print("\nPlease enter the number corresponding to the option you wish to select: ");
    int selected = 0;
    while (true) {
      selected = UserInput.getInt();
      if (selected < 1 || selected > items.length) {
        System.out.print("Please enter a valid option: ");
      } else {
        break;
      }
    }
    return items[selected - 1];
  }

  public static <T> T printAndGetSelection(List<T> items) throws NoItemsException {
    if (items == null || items.size() <= 0) {
      throw new NoItemsException();
    }

    for (int i = 0; i < items.size(); i++) {
      System.out.printf("%d: %s\n", i + 1, items.get(i).toString());
    }

    System.out.print("\nPlease enter the number corresponding to the option you wish to select: ");
    int selected = 0;
    while (true) {
      selected = UserInput.getInt();
      if (selected < 1 || selected > items.size()) {
        System.out.print("Please enter a valid option: ");
      } else {
        break;
      }
    }
    return items.get(selected - 1);
  }
}
