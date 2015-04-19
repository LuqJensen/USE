package unifiedshoppingexperience;

import java.util.ArrayList;

/**
 *
 * @author Gruppe 12
 */
public class Customer
{
    private String ID;
    private String name;
    private String surName;
    private String email;
    private String phoneNumber;
    ShoppingCart shoppingCart; 
    ArrayList wishList;

    public Customer(String ID, String name, String surName, String email, String phoneNumber)
    {
        this.ID = ID;
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shoppingCart = new ShoppingCart();
        this.wishList = new ArrayList();
    }

    public Cart addToCart(Product product)
    {
        shoppingCart.addProduct(product);
        return shoppingCart;
    }

    public Cart viewCart(int cartID)
    {
        return shoppingCart;
    }

    public Order createOrder(Cart cart)
    {
        return null;
    }
}
