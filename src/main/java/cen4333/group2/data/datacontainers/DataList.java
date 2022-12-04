package cen4333.group2.data.datacontainers;

import java.util.ArrayList;
import java.util.List;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.data.datainterfaces.Prototype;
import cen4333.group2.data.datainterfaces.Duplicate;

public class DataList<T extends Prototype<T> & QueryResult & Duplicate> implements Prototype<DataList<T>>, Duplicate {

  public List<T> data = new ArrayList<T>();

  public DataList() {}

  @SuppressWarnings("unchecked")
  @Override
  public Duplicate duplicate() {
    DataList<T> dataList = new DataList<T>();
    for (T dataItem : data) {
      dataList.data.add((T) dataItem.duplicate());
    }
    return dataList;
  }

  @Override
  public DataList<T> createInstance() {
    return new DataList<T>();
  }
  
}
