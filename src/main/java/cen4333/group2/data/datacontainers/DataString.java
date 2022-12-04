package cen4333.group2.data.datacontainers;

import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.data.datainterfaces.Duplicate;

public class DataString implements Prototype<DataString>, Duplicate {

  public String value;

  public DataString(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public Duplicate duplicate() {
    return new DataString(value);
  }

  @Override
  public DataString createInstance() {
    return new DataString(null);
  }
  
}
