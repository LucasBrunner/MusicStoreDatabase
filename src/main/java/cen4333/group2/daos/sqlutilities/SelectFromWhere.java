package cen4333.group2.daos.sqlutilities;

public interface SelectFromWhere {
  /**
   * 
   * @param where
   * @return a string with a SQL SELECT query for this object. 
   * This query contains all of the data used by this object's QueryResult interface (if there is one) as well as the ID column.
   * The query does not have a semicolon at the end.
   */
  public String getSelectFromWhereQuery(String where);
  public String getSelectCountQuery(String where);
  public String getIdColumnName();
}
