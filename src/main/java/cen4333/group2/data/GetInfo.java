package cen4333.group2.data;

public interface GetInfo {
  public default String getName() { return toString(); };
  public default String getDisplayName() { return toString(); };
  public default String getShortInfo() { return toString(); };
  public default String getFullInfo() { return toString(); };
}
