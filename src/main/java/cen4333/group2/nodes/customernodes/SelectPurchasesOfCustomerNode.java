package cen4333.group2.nodes.customernodes;

import java.sql.SQLException;

import cen4333.group2.Node;
import cen4333.group2.daos.sqlutilities.SelectFromWhereIter;
import cen4333.group2.data.Customer;
import cen4333.group2.data.DataWithId;
import cen4333.group2.data.Purchase;
import cen4333.group2.data.PurchaseAndCustomer;
import cen4333.group2.nodes.purchasenodes.ViewPurchaseNode;

public class SelectPurchasesOfCustomerNode extends Node {

  private DataWithId<Customer> customerWithId;

  public SelectPurchasesOfCustomerNode(DataWithId<Customer> customerWithId) {
    this.customerWithId = customerWithId;
  }

  @Override
  public String getName() {
    return "View purchases";
  }

  @Override
  public void runNode() {
    int pageSize = SelectFromWhereIter.getResultsAmount();
    while (true) {
      try {
        DataWithId<PurchaseAndCustomer> selectedPurchase = new SelectFromWhereIter<PurchaseAndCustomer>(
          "WHERE `CustomerID` = " + customerWithId.id,
          new PurchaseAndCustomer(),
          pageSize
        ).userSelect(true, "purchase", true);
        if (selectedPurchase != null) {
          new ViewPurchaseNode(selectedPurchase.data.purchase, selectedPurchase.data.customer.data.person.data.fullName()).runNode();
        } else {
          break;
        }
       } catch (SQLException e) {}
    }
  }
  
}
