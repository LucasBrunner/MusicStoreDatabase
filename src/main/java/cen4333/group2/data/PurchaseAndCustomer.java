package cen4333.group2.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import cen4333.group2.daos.sqlutilities.QueryResult;
import cen4333.group2.daos.sqlutilities.SelectFrom;

public class PurchaseAndCustomer implements QueryResult, SelectFrom, Prototype, GetInfo {

  public DataWithId<Purchase> purchase;
  public DataWithId<Customer> customer;

  @SuppressWarnings("unchecked")
  @Override
  public Prototype duplicate() {
    PurchaseAndCustomer purchaseAndPerson = new PurchaseAndCustomer();
    purchaseAndPerson.purchase = (DataWithId<Purchase>) purchase.duplicate();
    purchaseAndPerson.customer = (DataWithId<Customer>) customer.duplicate();
    return null;
  }

  @Override
  public Prototype duplicateEmpty() {
    return new PurchaseAndCustomer();
  }

  @Override
  public String getSelectFromQuery(String where) {
    return """
      SELECT 
        `CustomerID`,
        `PersonID`,
        `FirstName`,
        `LastName`,
        `PhoneNumber`,
        `Email`,
        `Address`,

        `PurchaseID`,
        `Date`
      FROM 
        `purchase`
        INNER JOIN `customer` USING(`CustomerID`)
        INNER JOIN `person` USING(`PersonID`)
      """ + where;
  }

  @Override
  public String getSelectCountQuery(String where) {
    return new Purchase().getSelectCountQuery(where);
  }

  @Override
  public String getIdColumnName() {
    return "PurchaseID";
  }

  @Override
  public void fillWithResultSet(ResultSet results) throws SQLException {
    purchase = new DataWithId<Purchase>();
    purchase.id = results.getInt("PurchaseID");
    purchase.data = new Purchase();
    purchase.data.fillWithResultSet(results);

    customer = new DataWithId<Customer>();
    customer.id = results.getInt("PersonID");
    customer.data = new Customer();
    customer.data.fillWithResultSet(results);
  }

  @Override
  public String getDisplayName() {
    return String.format(
      """
      %s's %s order""",
      customer.data.person.data.fullName(),
      purchase.data.date.toString()
    );
  }
  
}
