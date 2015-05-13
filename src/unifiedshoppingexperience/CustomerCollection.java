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

    public CustomerCollection()
    {
        customerMap = new HashMap();
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
