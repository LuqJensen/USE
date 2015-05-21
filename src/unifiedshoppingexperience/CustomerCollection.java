package unifiedshoppingexperience;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import persistence.DatabaseConnection;

/**
 *
 * @author Gruppe12
 */
public class CustomerCollection
{

    private Map<String, Customer> customerMap;
    private Map<String, Customer> customerEmailMap;

    public CustomerCollection(DatabaseConnection db) throws SQLException
    {
        customerEmailMap = new HashMap();
        customerMap = new HashMap();

        ResultSet rs = db.Select("SELECT * FROM customer;");

        while (rs.next()) // for each tuple in ResultSet
        {
            Customer customer = new Customer(db, rs);
            customerMap.put(customer.getID(), customer);
        }
    }

    public void save(DatabaseConnection db) throws SQLException
    {
        String update = "UPDATE customer SET first_name = ?, sur_name = ?, email = ?, phonenumber = ?, address = ? WHERE id = ?;";
        String query = "INSERT INTO customer(id, first_name, sur_name, email, phonenumber, address) SELECT ?, ?, ?, ?, ?, ? WHERE NOT EXISTS(SELECT * FROM customer WHERE id = ?)";
        PreparedStatement customerUpdate = db.createPreparedStatement(update);
        PreparedStatement customerQuery = db.createPreparedStatement(query);

        for (Customer c : customerMap.values())
        {
            c.save(db, customerQuery, customerUpdate);
        }

        customerUpdate.executeBatch();
        customerQuery.executeBatch();
    }

    public void setEmail(String email, String customerID)
    {

        Customer c = customerMap.get(customerID);

        if (c == null)
        {
            return;
        }
        customerEmailMap.put(email, c);
        c.setEmail(email);

    }

    public Customer getRegisteredCustomer(String email)
    {
        Customer c = customerEmailMap.get(email);

        return c;
    }

    public Customer getCustomer(String customerID)
    {
        Customer c = customerMap.get(customerID);

        if (c == null)
        {
            c = new Customer(customerID);
            customerMap.put(customerID, c);
        }

        return c;
    }
}
