package cen4333.group2.utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import cen4333.group2.errors.NoItemsException;

public class Utility {
  public enum SearchType {
    Name,
    CustomerId,
    PersonId;

    @Override
    public String toString() {
      switch (this) {
        case Name:
          return "Name";
      
        case CustomerId:
          return "Customer ID";
    
        case PersonId:
          return "Person ID";
    
        default:
          return "";
      }
    }
  }

  public static String bigDecimalAsCurrencyString(BigDecimal value) {
    return NumberFormat.getCurrencyInstance().format(value);
  }

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

  public static String addWildcards(String input) {
    return String.format("%%%s%%", input);
  }
}
