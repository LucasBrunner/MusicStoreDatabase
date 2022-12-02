package cen4333.group2.nodes.customernodes;

import java.sql.SQLException;

import cen4333.group2.Node;
import cen4333.group2.daos.sqlutilities.SelectFromWhereIter;
import cen4333.group2.data.Customer;
import cen4333.group2.data.DataWithId;
import cen4333.group2.data.Purchase;

public class ViewPurchasesNode extends Node {

  private DataWithId<Customer> customerWithId;

  public ViewPurchasesNode(DataWithId<Customer> customerWithId) {
    this.customerWithId = customerWithId;
  }

  @Override
  public String getName() {
    return "View purchases";
  }

  @Override
  public void runNode() {
    try {
      SelectFromWhereIter<Purchase> purchaseIter = new SelectFromWhereIter<Purchase>(
        "WHERE `CustomerID` = " + customerWithId.id,
        new Purchase(),
        SelectFromWhereIter.getResultsAmount()
      );
    } catch (SQLException e) {}

    while (true) {
      
    }
  }
  
}
