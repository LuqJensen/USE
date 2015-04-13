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
        this.wishList = new ArrayList();
        this.shoppingCart = new ShoppingCart();
    }

    public void addToCart(Product product)
    {

    }

    public void viewCart(int cartID)
    {

    }

    public void createOrder(Cart cart)
    {

    }
}
