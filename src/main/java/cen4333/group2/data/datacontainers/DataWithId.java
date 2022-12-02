package cen4333.group2.data.datacontainers;

import cen4333.group2.data.datainterfaces.CreateInstance;
import cen4333.group2.data.datainterfaces.Duplicate;

public class DataWithId<T extends CreateInstance & Duplicate> implements CreateInstance, Duplicate {
  public int id;
  public T data;

  public DataWithId() {}

  public DataWithId(T data) {
    this.data = data;
  }

  @SuppressWarnings("unchecked") // This wouldn't have to be here in Rust.
  @Override
  public Duplicate duplicate() {
    DataWithId<T> dataWithId = new DataWithId<T>();
    dataWithId.id = id;
    if (data != null) {
      dataWithId.data = (T) data.duplicate();
    }
    return null;
  }
  @Override
  public CreateInstance createInstance() {
    return new DataWithId<T>();
  }
}
