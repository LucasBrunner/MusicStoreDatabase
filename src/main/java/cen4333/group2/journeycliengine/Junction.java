package cen4333.group2.journeycliengine;

import cen4333.group2.errors.JunctionNotFoundException;

public abstract class Junction {
  /**
   * @return An array of paths which lead out from this junction.
   */
  public abstract Path[] paths();

  /**
   * @return A new instance of this junction.
   */
  public abstract Junction newJunction();

  public Path selectPath() throws JunctionNotFoundException {
    Path output = null;

    // Print padding
    System.out.println();

    Path[] paths = paths();

    if ( paths.length == 1 ) {
      System.out.printf("Continuing to path \"%s\".\n", paths[0].getName());
      output = paths[0];
    } else {
      for (int i = 0; i < paths.length; i++) {
        System.out.printf(
          "%d: %s\n", 
          (i + 1), 
          paths[i].getName()
        );
      }

      System.out.print("\nPlease enter the number corresponding to the option you wish to select: ");
      int selected = 0;
      while (true) {
        selected = UserInput.getInt();
        if (selected < 1 || selected > paths.length) {
          System.out.print("Please enter a valid option: ");
        } else {
          break;
        }
      }
      output = paths[selected - 1];
    }

    if (output != null) {
      return output;
    } else {
      throw new JunctionNotFoundException("""
        Something went wrong, there is nowhere to go from here!
        Returning to the Main Menu.
        """);
    }
  }

}
