package unifiedshoppingexperience;

import java.util.HashMap;
import java.util.Map;

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
