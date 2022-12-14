package cen4333.group2.sqlutilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cen4333.group2.data.datacontainers.DataWithId;
import cen4333.group2.data.datacontainers.ObjectWithValue;
import cen4333.group2.data.datainterfaces.DisplayText;
import cen4333.group2.data.datainterfaces.Duplicate;
import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.errors.NoItemsException;
import cen4333.group2.utility.UserInput;
import cen4333.group2.utility.ObjectSelector;

public class GetIter <T extends QueryResult & Get<T> & Prototype<T> & DisplayText & Duplicate & PrimaryKey> {
  private String where;
  private T CreateInstance;
  private int stepSize;

  private int currentPosition = 0;
  private int rowCount;

  public GetIter(String where, T CreateInstance, int stepSize) throws SQLException {
    this.where = where;
    this.CreateInstance = CreateInstance;
    this.stepSize = stepSize;

    this.rowCount = CreateInstance.getCount(where);
  }

  public static int getResultsAmount() {
    int amount = 0;
    System.out.print("How many results per page (1-20): ");
    while (true) {
      amount = UserInput.getInt();
      if (amount < 1) {
        System.out.print("Must be more than 0: ");
      } else if (amount > 20) {
        System.err.print("Cannot be greater than 20: ");
      } else {
        break;
      }
    }
    return amount;
  }

  /**
   * Gets the current page.
   * @return 
   */
  public List<DataWithId<T>> getPage() throws SQLException, ClassCastException {
    String limit = String.format(
      """
        \nLIMIT %d
        OFFSET %d
      """, 
      stepSize + 1,
      currentPosition * stepSize
    );
    
    List<DataWithId<T>> output = CreateInstance.getList(where + limit, CreateInstance);
    while (output.size() > stepSize) {
      output.remove(output.size() - 1);
    }
    return output;
  }

  /**
   * Moves to the next page.
   * @return true if there was a page to move to.
   */
  public void nextPage() {
    if (hasNextPage()) {
      currentPosition += 1;
    }
  }

  /**
   * Moves to the previous page.
   * @return true if there was a page to move to.
   */
  public void previousPage() {
    if (currentPosition > 0) {
      currentPosition -= 1;
    } 
  }

  public boolean hasNextPage() {
    return (rowCount / stepSize) > currentPosition;
  }

  public boolean hasPreviousPage() {
    return currentPosition > 0;
  }

  public int getOffset() {
    return currentPosition * stepSize;
  }

  public int getStepSize() {
    return stepSize;
  }

  /**
   * Lets the user select an item from the iterator.
   * @return the object the user selects or null if the user cancels.
   * @throws SQLException
   * @throws ClassCastException
   */
  public DataWithId<T> userSelect(boolean cancelable, String objectName, boolean displayIds) throws ClassCastException, SQLException {
    List<DataWithId<T>> page = new ArrayList<DataWithId<T>>();
    List<ObjectWithValue<String, Integer>> selections = new ArrayList<ObjectWithValue<String, Integer>>();
    while (true) {
      System.out.printf(
        "\nPrinting %s %d to %d. Select one to view it:\n", 
        objectName,
        getOffset() + 1, 
        getOffset() + getStepSize()
      );
    
      page = getPage();
      selections.clear();
      for (int i = 0; i < page.size(); i++) {
        String displayString = displayIds ? "ID: " + page.get(i).id + ", " : "";
        displayString = displayString + page.get(i).data.getDisplayText();
        selections.add(new ObjectWithValue<String, Integer>(
          displayString, 
          selections.size()
          ));
      }

      if (hasNextPage()) {
        selections.add(new ObjectWithValue<String, Integer>("Next page", -1));
      }
      if (hasPreviousPage()) {
        selections.add(new ObjectWithValue<String, Integer>("Previous page", -2));
      }
      selections.add(new ObjectWithValue<String, Integer>("Cancel", -3));

      Integer selection = null;
      try {
        selection = ObjectSelector.printAndGetSelection(selections).value;
      } catch (NoItemsException e) {}

      if (selection == -1) {
        nextPage();
      } else if (selection == -2) {
        previousPage();
      } else if (selection == -3) {
        return null;
      } else if (selection >= 0 && selection <= page.size()) {
        return page.get(selection);
      } else {
        System.out.println("Invalid state! Proceeding to next valid state.");
      }
    }
  }
}
