package cen4333.group2.errors;

public class AppBaseException extends Exception {
  private String displayText;

  public AppBaseException(String displayText) {
    this.displayText = displayText;
  }

  public String getDisplayText() {
    return displayText;
  }
}
