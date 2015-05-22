package persistence;

import java.util.Collection;
import shared.Address;
import unifiedshoppingexperience.Cart;
import unifiedshoppingexperience.Customer;
import unifiedshoppingexperience.Order;
import unifiedshoppingexperience.Product;
import unifiedshoppingexperience.ProductLine;
import unifiedshoppingexperience.ShoppingCart;
import unifiedshoppingexperience.WishList;

/**
 *
 * @author Lucas
 */
public interface IRepository
{
    Collection<Customer> getAllCustomers();

    Collection<Product> getAllProducts();

    void saveAllCustomers();

    void saveAllAddresses();

    void saveAllOrders();

    void saveAllCarts();

    void saveAllWishLists();

    void saveAllProducts();

    void saveAllProductLines();

    void saveAll();

    void loadAll();

    public void persist(Customer c);

    public void persist(Address a);

    public void persist(ShoppingCart c);

    public void persist(Product p);

    public void persist(Order o, Customer c);

    public void persist(WishList w, Customer c);

    public void persist(ProductLine pl, Cart c);
}
