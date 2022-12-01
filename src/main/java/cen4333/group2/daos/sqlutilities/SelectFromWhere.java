package cen4333.group2.daos.sqlutilities;

public interface SelectFromWhere {
  public String getSelectFromWhereQuery(String where);
  public String getSelectCountQuery(String where);
}
