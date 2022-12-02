package cen4333.group2.data.datainterfaces;

public interface Prototype {
  /**
   * @return an object which can be cast to the parent type with duplicate data.
   */
  public Prototype duplicate();
  /**
   * @return an empty object which can be cast to the parent type.
   */
  public Prototype duplicateEmpty();
}
