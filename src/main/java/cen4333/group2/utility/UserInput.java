package cen4333.group2.utility;

import java.util.Scanner;

public class UserInput {
  private static Scanner scanner;

  static {
    createScanner();
  }

  public static void close() {
    if (scanner != null) {
      scanner.close();
    }
  }

  private static void createScanner() {
    scanner = new Scanner(System.in);
  }

  public static String getString() {
    try {
      return scanner.nextLine();
    } catch (IllegalStateException e) {
      createScanner();
      return scanner.nextLine();
    }
  }

  public static int getInt() {
    while (true) {
      try {
        return Integer.parseInt(getString());
      } catch (NumberFormatException e) {
        System.out.print("Please enter a whole number: ");
      }
    }
  }

  public static double getDouble() {
    while (true) {
      try {
        return Double.parseDouble(getString());
      } catch (NumberFormatException e) {
        System.out.print("Please enter a number: ");
      }
    }
  }

  public static char getChar() {
    return getString().trim().charAt(0);
  }

  public enum YesNo {
    YES,
    NO
  }

  public static YesNo getYesNo() {
    while (true) {
      System.out.print("Please enter either yes or no: ");
      char input = Character.toLowerCase(getChar());
      if (input == 'y') {
        return YesNo.YES;
      } else if (input == 'n') {
        return YesNo.NO;
      }
    }
  }

  public static boolean getBool() { return getBool("true", "false"); }

  public static boolean getBool(String trueString, String falseString) {
    if (trueString == falseString) {
      return true;
    }

    while (true) {
      String input = getString();

      boolean isTrue = true;
      int lengthReachedTrue = 0;
      for (int i = 0; i < input.length() && i < trueString.length() && isTrue == true; i++) {
        isTrue = Character.toLowerCase(input.charAt(i)) == Character.toLowerCase(trueString.charAt(i));
        lengthReachedTrue += isTrue ? 1 : 0;
      }

      boolean isFalse = true;
      int lengthReachedFalse = 0;
      for (int i = 0; i < input.length() && i < falseString.length() && isFalse == true; i++) {
        isFalse = Character.toLowerCase(input.charAt(i)) == Character.toLowerCase(falseString.charAt(i));
        lengthReachedFalse += isFalse ? 1 : 0;
      }
      
      if (lengthReachedTrue != lengthReachedFalse) {
        if (isTrue == isFalse) {
          return lengthReachedTrue > lengthReachedFalse;
        } else {
          if (isTrue == true) {
            return true;
          } else if (isFalse == true) {
            return false;
          }
        }
      } else if (trueString.length() > falseString.length()) {
        if (isFalse == true) {
          return false;
        }
      } else if (trueString.length() < falseString.length()) {
        if (isTrue == true) {
          return true;
        }
      }
      System.out.printf("Please enter either %s or %s.\n", trueString, falseString);
    }
  }
  
}
