package cen4333.group2.utility;

public class ObjectWithValue<T, U> {
  public T object;
  public U value;

  public ObjectWithValue(T object, U value) {
    this.object = object;
    this.value = value;
  }

  @Override
  public String toString() {
    return object.toString();
  }
}
