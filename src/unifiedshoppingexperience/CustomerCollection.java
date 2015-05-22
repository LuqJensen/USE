package unifiedshoppingexperience;

import java.util.HashMap;
import java.util.Map;
import persistence.DataStore;

/**
 *
 * @author Gruppe12
 */
public class CustomerCollection
{
    private Map<String, Customer> customerMap;
    private Map<String, Customer> customerEmailMap;

    public CustomerCollection()
    {
        customerEmailMap = new HashMap();
        customerMap = new HashMap();

        for (Customer c : DataStore.getPersistence().getAllCustomers())
        {
            customerMap.put(c.getID(), c);
            customerEmailMap.put(c.getEmail(), c);
        }
    }

    /**
     * Sets an email to a specified customer and puts the email in a map
     * pointing to the specified customer.
     *
     * @param email The email to be set.
     * @param customerID The ID of the customer.
     */
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

    /**
     * Gets a registered customer by email.
     *
     * @param email The email of the customer.
     * @return Returns a registered customer.
     */
    public Customer getRegisteredCustomer(String email)
    {
        Customer c = customerEmailMap.get(email);

        return c;
    }

    /**
     * Gets a customer by ID, and creates a customer if there is no customer
     * with this ID. It is not certain that the customer returned is registered.
     *
     * @param customerID The ID of the customer.
     * @return Returns a customer with specified ID.
     */
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
