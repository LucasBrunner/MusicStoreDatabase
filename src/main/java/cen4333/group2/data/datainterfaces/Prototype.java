package cen4333.group2.data.datainterfaces;

public interface Prototype<T> {
  /**
   * @return an empty object which can be cast to the parent type.
   */
  public T createInstance();
}
