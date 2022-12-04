package cen4333.group2.data.datacontainers;

import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.data.datainterfaces.Duplicate;

public class DataWithId<T extends Prototype<T> & Duplicate> implements Prototype<DataWithId<T>>, Duplicate {
  public int id;
  public T data;

  public DataWithId() {}

  public DataWithId(T data) {
    this.data = data;
  }

  public DataWithId(T data, int id) {
    this(data);
    this.id = id;
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
  public DataWithId<T> createInstance() {
    return new DataWithId<T>();
  }
}
