/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import shared.Address;
import shared.OrderStatus;
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
public class DataMapping implements IRepository
{
    private DatabaseConnection db;
    //private final Map<Class, Map> persistence;

    // Each key reflects the type of the respective entity's private key.
    private final Map<String, Customer> customerStore;
    private final Map<Integer, Address> addressStore;
    private final Map<Integer, Cart> cartStore;
    private final Map<String, Product> productStore;

    // Each key reflects the type of Customer's private key.
    private final Map<String, HashSet<Order>> orderStore;
    private final Map<String, HashSet<WishList>> wishlistStore;
    private final Map<Integer, HashSet<ProductLine>> productlineStore;

    @Override
    public void persist(Customer c)
    {
        customerStore.put(c.getID(), c);
    }

    @Override
    public void persist(Address a)
    {
        addressStore.put(a.getID(), a);
    }

    @Override
    public void persist(ShoppingCart c)
    {
        cartStore.put(c.getID(), c);
    }

    @Override
    public void persist(Product p)
    {
        productStore.put(p.getModel(), p);
    }

    @Override
    public void persist(Order o, Customer c)
    {
        HashSet hs = orderStore.get(c.getID());
        if (hs == null)
        {
            hs = new HashSet();
        }
        hs.add(o);
        orderStore.put(c.getID(), hs);
    }

    @Override
    public void persist(WishList w, Customer c)
    {
        HashSet hs = wishlistStore.get(c.getID());
        if (hs == null)
        {
            hs = new HashSet();
        }
        hs.add(w);
        wishlistStore.put(c.getID(), hs);
    }

    @Override
    public void persist(ProductLine pl, Cart c)
    {
        HashSet hs = productlineStore.get(c.getID());
        if (hs == null)
        {
            hs = new HashSet();
        }
        hs.add(pl);
        productlineStore.put(c.getID(), hs);
    }

    public DataMapping()
    {
        customerStore = new HashMap();
        addressStore = new HashMap();
        orderStore = new HashMap();
        cartStore = new HashMap();
        wishlistStore = new HashMap();
        productlineStore = new HashMap();
        productStore = new HashMap();

        try
        {
            db = new DatabaseConnection();
        }
        catch (SQLException ex)
        {
            System.out.println("Could not connect to database, please check your settings.");
            ex.printStackTrace();
            System.exit(1);
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Could not find postgresql jdbc drivers. Please ensure that you have properly installed postgres.");
            System.exit(1);
        }
    }

    /**
     * It is best to let some controller call this method, as it may create an
     * infinite loop, if persist is used incorrectly in any of the domain
     * classes. Saves the trouble when debugging after maintenance.
     */
    @Override
    public final void loadAll()
    {
        // Order matters!!!!
        loadAddresses();
        loadProducts();
        loadProductLines();
        loadCartsAndWishLists();
        loadOrders();
        loadCustomers();
    }

    private void loadCartsAndWishLists()
    {
        String sql = "SELECT * FROM cart NATURAL FULL JOIN wishlist;";
        try
        {
            ResultSet rs = db.Select(sql);

            while (rs.next())
            {
                Integer cartID = rs.getInt(1);
                Map<Product, ProductLine> productLines = new HashMap();
                BigDecimal price = new BigDecimal(0.0);

                if (productlineStore.get(cartID) != null)
                {
                    for (ProductLine pl : productlineStore.get(cartID))
                    {
                        productLines.put(pl.getProduct(), pl);
                        price = price.add(pl.getTotalPrice());
                    }
                }

                String name = rs.getString(3);

                if (name != null)
                {
                    String customerID = rs.getString(2);
                    HashSet hs = wishlistStore.get(customerID);
                    if (hs == null)
                    {
                        hs = new HashSet();
                        wishlistStore.put(customerID, hs);
                    }
                    hs.add(new WishList(cartID, price, productLines, name));
                }
                else
                {
                    cartStore.put(cartID, new ShoppingCart(cartID, price, productLines));
                }
            }

        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    private void loadAddresses()
    {
        String sql = "SELECT * FROM address";
        try
        {
            ResultSet rs = db.Select(sql);
            while (rs.next())
            {
                addressStore.put(rs.getInt("id"), new Address(rs.getInt("id"), rs.getString("inhabitant_name"),
                                                              rs.getString("street_address"), rs.getInt("zip_code"), rs.getString("city"), rs.getString("country")));
            }
        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    private void loadProductLines()
    {
        try
        {
            ResultSet rs = db.Select("SELECT * FROM productline;");
            while (rs.next())
            {
                Product product = productStore.get(rs.getString(2));
                int quantity = rs.getInt(3);

                ProductLine productLine = new ProductLine(product, quantity);

                HashSet hs = productlineStore.get(rs.getInt(1));
                if (hs == null)
                {
                    hs = new HashSet();
                    productlineStore.put(rs.getInt(1), hs);
                }
                hs.add(productLine);
            }
        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    private void loadOrders()
    {
        String sql = "SELECT * FROM \"order\"";
        try
        {
            ResultSet rs = db.Select(sql);

            while (rs.next())
            {
                Integer id = rs.getInt(2);
                BigDecimal price = rs.getBigDecimal(3);
                OrderStatus status = OrderStatus.values()[rs.getInt(4)];
                String paymentMethod = rs.getString(5);
                Date purchaseDate = rs.getDate(6);
                Date dispatchedDate = rs.getDate(7);
                Cart cart = cartStore.get(rs.getInt(8));
                Address address = addressStore.get(rs.getInt(9));

                Order order = new Order(id, price, status, paymentMethod, purchaseDate, dispatchedDate, cart, address);

                HashSet hs = orderStore.get(rs.getString(1));
                if (hs == null)
                {
                    hs = new HashSet();
                    orderStore.put(rs.getString(1), hs);
                }
                hs.add(order);
            }

        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    private void loadCustomers()
    {
        try
        {
            ResultSet rs = db.Select("SELECT * FROM customer;");

            while (rs.next())
            {
                String ID = rs.getString(1);
                String firstName = rs.getString(2);
                String surName = rs.getString(3);
                String email = rs.getString(4);
                String phoneNumber = rs.getString(5);
                Address defaultDeliveryAddress = addressStore.get(rs.getInt(6));
                ShoppingCart shoppingCart = (ShoppingCart)cartStore.get(rs.getInt(7));
                HashSet wl = wishlistStore.get(ID);
                ArrayList<WishList> wishLists = wl == null ? new ArrayList() : new ArrayList(wl);

                Map<Integer, Order> orders = new HashMap();

                if (orderStore.get(ID) != null)
                {
                    for (Order o : orderStore.get(ID))
                    {
                        orders.put(o.getID(), o);
                    }
                }

                Customer customer = new Customer(ID, firstName, surName, email, phoneNumber, defaultDeliveryAddress, shoppingCart, wishLists, orders);
                customerStore.put(customer.getID(), customer);
            }

        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }

    }

    private void loadProducts()
    {
        try
        {
            ResultSet rs = db.Select("SELECT * FROM product;");

            while (rs.next())
            {
                Product product = new Product(rs.getString(1), rs.getBigDecimal(2), rs.getString(3), rs.getString(4));
                productStore.put(product.getModel(), product);
            }
        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Collection<Product> getAllProducts()
    {
        return productStore.values();
    }

    @Override
    public Collection<Customer> getAllCustomers()
    {
        return customerStore.values();
    }

    @Override
    public void saveAll()
    {
        // Order matters!!!!!
        saveAllAddresses();
        saveAllProducts();
        saveAllCarts();
        saveAllWishLists();
        saveAllProductLines();
        saveAllOrders();
        saveAllCustomers();
    }

    @Override
    public void saveAllAddresses()
    {
        try
        {
            String update = "UPDATE address SET inhabitant_name = ?, street_address = ?, zip_code = ?, city = ?, country = ? WHERE id = ?;";
            String insert = "INSERT INTO address(id, inhabitant_name, street_address, zip_code, city, country) "
                            + "SELECT ?, ?, ?, ?, ?, ? WHERE NOT EXISTS (SELECT * FROM address WHERE id = ?);";

            PreparedStatement addressUpdate = db.createPreparedStatement(update);
            PreparedStatement addressInsert = db.createPreparedStatement(insert);

            for (Address a : addressStore.values())
            {
                addressUpdate.setString(1, a.getInhabitantName());
                addressUpdate.setString(2, a.getStreetAddress());
                addressUpdate.setInt(3, a.getZipCode());
                addressUpdate.setString(4, a.getCity());
                addressUpdate.setString(5, a.getCountry());
                addressUpdate.setInt(6, a.getID());
                addressUpdate.addBatch();

                addressInsert.setInt(1, a.getID());
                addressInsert.setString(2, a.getInhabitantName());
                addressInsert.setString(3, a.getStreetAddress());
                addressInsert.setInt(4, a.getZipCode());
                addressInsert.setString(5, a.getCity());
                addressInsert.setString(6, a.getCountry());
                addressInsert.setInt(7, a.getID());
                addressInsert.addBatch();
            }

            addressUpdate.executeBatch();
            addressInsert.executeBatch();
        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveAllCarts()
    {
        try
        {
            //String update = "UPDATE cart SET customer_id = ? WHERE id = ?;";
            String insert = "INSERT INTO cart(id) SELECT ? WHERE NOT EXISTS(SELECT * FROM cart WHERE id = ?);";
            //PreparedStatement cartUpdate = db.createPreparedStatement(update);
            PreparedStatement cartInsert = db.createPreparedStatement(insert);

            for (Cart c : cartStore.values())
            {
                cartInsert.setInt(1, c.getID());
                cartInsert.setInt(2, c.getID());
                cartInsert.addBatch();
            }

            cartInsert.executeBatch();

        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveAllWishLists()
    {
        try
        {
            String update = "UPDATE wishlist SET name = ? WHERE id = ?;";
            String insert = "INSERT INTO wishlist(id, name) SELECT ?, ? WHERE NOT EXISTS(SELECT * FROM wishlist WHERE name = ?);";
            PreparedStatement wishlistUpdate = db.createPreparedStatement(update);
            PreparedStatement wishlistInsert = db.createPreparedStatement(insert);

            for (String customerID : wishlistStore.keySet())
            {
                for (WishList wl : wishlistStore.get(customerID))
                {
                    wishlistUpdate.setString(1, customerID);
                    wishlistUpdate.setString(2, wl.getName());
                    wishlistUpdate.addBatch();

                    wishlistInsert.setString(1, customerID);
                    wishlistInsert.setString(2, wl.getName());
                    wishlistInsert.setString(3, customerID);
                    wishlistInsert.addBatch();
                }
            }

            wishlistUpdate.executeBatch();
            wishlistInsert.executeBatch();
        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public void saveAllOrders()
    {
        try
        {
            String update = "UPDATE \"order\" SET customer_id = ?, price = ?, status = ?, payment_method = ?, "
                            + "purchase_date = ?, dispatched_date = ?, cart_id = ?, delivery_address = ? WHERE id = ?;";
            String insert = "INSERT INTO \"order\" (customer_id, id, price, status, payment_method, purchase_date, dispatched_date, cart_id, delivery_address)"
                            + " SELECT ?, ?, ?, ?, ?, ?, ?, ?, ? WHERE NOT EXISTS(SELECT * FROM \"order\" WHERE id = ?)";
            PreparedStatement orderUpdate = db.createPreparedStatement(update);
            PreparedStatement orderInsert = db.createPreparedStatement(insert);

            for (String customerID : orderStore.keySet())
            {
                for (Order o : orderStore.get(customerID))
                {
                    if (o.getStatus() == OrderStatus.UNPAID)
                    {
                        continue;
                    }

                    orderUpdate.setString(1, customerID);

                    orderInsert.setString(1, customerID);

                    orderUpdate.setInt(9, o.getID());
                    orderUpdate.setBigDecimal(2, o.getPrice());
                    orderUpdate.setInt(3, o.getStatus().ordinal());
                    orderUpdate.setString(4, o.getPaymentMethod());

                    orderInsert.setInt(2, o.getID());
                    orderInsert.setInt(10, o.getID());
                    orderInsert.setBigDecimal(3, o.getPrice());
                    orderInsert.setInt(4, o.getStatus().ordinal());
                    orderInsert.setString(5, o.getPaymentMethod());

                    if (o.getPurchaseDate() == null)
                    {
                        orderUpdate.setNull(5, java.sql.Types.DATE);
                        orderInsert.setNull(6, java.sql.Types.DATE);
                    }
                    else
                    {
                        orderUpdate.setDate(5, new java.sql.Date(o.getPurchaseDate().getTime()));
                        orderInsert.setDate(6, new java.sql.Date(o.getPurchaseDate().getTime()));
                    }
                    if (o.getDispatchedDate() == null)
                    {
                        orderUpdate.setNull(6, java.sql.Types.DATE);
                        orderInsert.setNull(7, java.sql.Types.DATE);
                    }
                    else
                    {
                        orderInsert.setDate(6, new java.sql.Date(o.getDispatchedDate().getTime()));
                        orderInsert.setDate(7, new java.sql.Date(o.getDispatchedDate().getTime()));
                    }

                    orderUpdate.setInt(7, o.getCartID());
                    orderInsert.setInt(8, o.getCartID());

                    if (o.getAddress() == null)
                    {
                        orderUpdate.setNull(8, java.sql.Types.INTEGER);
                        orderInsert.setNull(9, java.sql.Types.INTEGER);
                    }
                    else
                    {
                        orderUpdate.setInt(8, o.getAddress().getID());
                        orderInsert.setInt(9, o.getAddress().getID());
                    }

                    orderUpdate.addBatch();
                    orderInsert.addBatch();
                }
            }

            orderUpdate.executeBatch();
            orderInsert.executeBatch();
        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveAllCustomers()
    {
        String update = "UPDATE customer SET first_name = ?, sur_name = ?, email = ?, phonenumber = ?, address = ?, shopping_cart = ? WHERE id = ?;";
        String insert = "INSERT INTO customer(id, first_name, sur_name, email, phonenumber, address, shopping_cart)"
                        + " SELECT ?, ?, ?, ?, ?, ?, ? WHERE NOT EXISTS(SELECT * FROM customer WHERE id = ?)";
        try
        {
            PreparedStatement customerUpdate = db.createPreparedStatement(update);
            PreparedStatement customerInsert = db.createPreparedStatement(insert);

            for (Customer c : customerStore.values())
            {
                customerUpdate.setString(1, c.getFirstName());
                customerUpdate.setString(2, c.getSurname());
                customerUpdate.setString(3, c.getEmail());
                customerUpdate.setString(4, c.getPhoneNumber());

                customerInsert.setString(1, c.getID());
                customerInsert.setString(2, c.getFirstName());
                customerInsert.setString(3, c.getSurname());
                customerInsert.setString(4, c.getEmail());
                customerInsert.setString(5, c.getPhoneNumber());

                if (c.getDefaultDeliveryAddress() == null)
                {
                    customerUpdate.setNull(5, java.sql.Types.INTEGER);
                    customerInsert.setNull(6, java.sql.Types.INTEGER);
                }
                else
                {
                    customerUpdate.setInt(5, c.getDefaultDeliveryAddress().getID());
                    customerInsert.setInt(6, c.getDefaultDeliveryAddress().getID());
                }

                customerUpdate.setInt(6, c.getShoppingCart().getID());
                customerUpdate.setString(7, c.getID());

                customerInsert.setInt(7, c.getShoppingCart().getID());
                customerInsert.setString(8, c.getID());

                customerInsert.addBatch();
                customerUpdate.addBatch();
            }

            customerUpdate.executeBatch();
            customerInsert.executeBatch();
        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveAllProducts()
    {
        try
        {
            String update = "UPDATE product SET price = ?, \"type\" = ?, name = ? WHERE model = ?;";
            String insert = "INSERT INTO product(model, price, \"type\", name)"
                            + " SELECT ?, ?, ?, ? WHERE NOT EXISTS(SELECT * FROM product WHERE model = ?);";
            PreparedStatement productInsert = db.createPreparedStatement(insert);
            PreparedStatement productUpdate = db.createPreparedStatement(update);

            for (Product p : productStore.values())
            {
                productUpdate.setBigDecimal(1, p.getPrice());
                productUpdate.setString(2, p.getType());
                productUpdate.setString(3, p.getName());
                productUpdate.setString(4, p.getModel());
                productUpdate.addBatch();

                productInsert.setString(1, p.getModel());
                productInsert.setString(5, p.getModel());
                productInsert.setBigDecimal(2, p.getPrice());
                productInsert.setString(3, p.getType());
                productInsert.setString(4, p.getName());
                productInsert.addBatch();
            }

            productInsert.executeBatch();
            productUpdate.executeBatch();
        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveAllProductLines()
    {
        try
        {
            String update = "UPDATE productline SET product_model = ?, quantity = ? WHERE cart_id = ?;";
            String insert = "INSERT INTO productline(cart_id, product_model, quantity) SELECT ?, ?, ? WHERE NOT EXISTS(SELECT * FROM productline WHERE cart_id = ? AND product_model = ?);";
            PreparedStatement productlineUpdate = db.createPreparedStatement(update);
            PreparedStatement productlineInsert = db.createPreparedStatement(insert);

            // Saving products to DB is done in assortment.
            for (Integer cartID : productlineStore.keySet())
            {
                for (ProductLine pl : productlineStore.get(cartID))
                {
                    productlineUpdate.setString(1, pl.getProduct().getModel());
                    productlineUpdate.setInt(2, pl.getQuantity());
                    productlineUpdate.setInt(3, cartID);
                    productlineUpdate.addBatch();

                    productlineInsert.setInt(1, cartID);
                    productlineInsert.setString(2, pl.getProduct().getModel());
                    productlineInsert.setInt(3, pl.getQuantity());
                    productlineInsert.setInt(4, cartID);
                    productlineInsert.setString(5, pl.getProduct().getModel());
                    productlineInsert.addBatch();
                }
            }

            productlineUpdate.executeBatch();
            productlineInsert.executeBatch();
        }
        catch (SQLException ex)
        {
            SQLException innerException = ex.getNextException();
            if (innerException != null)
            {
                innerException.printStackTrace();
            }
            else
            {
                ex.printStackTrace();
            }
        }
    }
}
