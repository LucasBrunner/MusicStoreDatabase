package cen4333.group2.data;

import java.util.ArrayList;
import java.util.List;

import cen4333.group2.daos.sqlutilities.QueryResult;

public class DataList<T extends Prototype & QueryResult> implements Prototype {

  public List<T> data = new ArrayList<T>();

  public DataList() {}

  @SuppressWarnings("unchecked")
  @Override
  public Prototype duplicate() {
    DataList<T> dataList = new DataList<T>();
    for (T dataItem : data) {
      dataList.data.add((T) dataItem.duplicate());
    }
    return dataList;
  }

  @Override
  public Prototype duplicateEmpty() {
    return new DataList<T>();
  }
  
}
