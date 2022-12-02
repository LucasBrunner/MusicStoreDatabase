package cen4333.group2.data;

public class DataString implements Prototype {

  public String value;

  public DataString(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public Prototype duplicate() {
    return new DataString(value);
  }

  @Override
  public Prototype duplicateEmpty() {
    return new DataString(null);
  }
  
}
