package cen4333.group2.data.datainterfaces;

public interface DisplayText {
  public default String getDisplayText() { return toString(); };
}
