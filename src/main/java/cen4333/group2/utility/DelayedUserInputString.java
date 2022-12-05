package cen4333.group2.utility;

public class DelayedUserInputString {
  public enum UserInputType {
    STRING,
    INTEGER,
    BOOLEAN,
    DOUBLE,
    CHAR,
    DATE_AS_SQL_STRING
  }

  private String outerStringValue;
  private String prompt;
  private UserInputType userInputType = UserInputType.STRING;
  
  public DelayedUserInputString(String outerStringValue, String prompt, UserInputType userInputType) {
    this.outerStringValue = outerStringValue;
    this.prompt = prompt;
    this.userInputType = userInputType;
  }

  public String get() {
    switch (userInputType) {
      case BOOLEAN:
        System.out.print(prompt);
        return String.format(
          outerStringValue, 
          UserInput.getBool()
        );
      case DOUBLE:
        System.out.print(prompt);
        return String.format(
          outerStringValue, 
          UserInput.getDouble()
        );
      case INTEGER:
        System.out.print(prompt);
        return String.format(
          outerStringValue, 
          UserInput.getInt()
        );
      case STRING:
        System.out.print(prompt);
        return String.format(
          outerStringValue, 
          UserInput.getString()
        );
      case CHAR:
        System.out.print(prompt);
        return String.format(
          outerStringValue, 
          UserInput.getChar()
        );
        case DATE_AS_SQL_STRING:
          System.out.print(prompt);
          return String.format(
            outerStringValue, 
            UserInput.getSqlDate("YYYY-MM-DD").toString()
          );
      default:
        return "";

    }
  }
}