package cen4333.group2.data;

public interface Prototype {
  /**
   * 
   * @return an object which can be cast to the parent type.
   */
  public Prototype duplicate();
}
