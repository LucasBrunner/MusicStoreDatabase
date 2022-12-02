package cen4333.group2.data.datacontainers;

import cen4333.group2.data.datainterfaces.Prototype;

public class DataWithId<T extends Prototype> implements Prototype {
  public int id;
  public T data;

  public DataWithId() {}

  public DataWithId(T data) {
    this.data = data;
  }

  @SuppressWarnings("unchecked") // This wouldn't have to be here in Rust.
  @Override
  public Prototype duplicate() {
    DataWithId<T> dataWithId = new DataWithId<T>();
    dataWithId.id = id;
    if (data != null) {
      dataWithId.data = (T) data.duplicate();
    }
    return null;
  }
  @Override
  public Prototype duplicateEmpty() {
    return new DataWithId<T>();
  }
}
