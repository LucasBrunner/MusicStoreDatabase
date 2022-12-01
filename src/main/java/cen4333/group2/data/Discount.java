package cen4333.group2.data;

import java.sql.Date;

public class Discount {
  public String Name;
  public Date startDate;
  public Date endDate;
  public String discountJson;

  @Override
  public String toString() {
    return String.format(
      """
        Name: %s
        Start date: %s
        End date: %s
      """, 
      Name,
      startDate.toLocalDate().toString(),
      endDate.toLocalDate().toString()
    );
  }

  public String toString(boolean showDates) {
    if (showDates) {
      return toString();
    } else {
      return String.format(
        """
          Name: %s
        """, 
        Name
      );
    }
  }
}
