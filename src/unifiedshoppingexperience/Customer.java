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
    ArrayList<WishList> wishList;

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
    
    public Customer(String ID)
    {
        this.ID = ID;
        this.shoppingCart = new ShoppingCart();
        this.wishList = new ArrayList();
    }

    public Cart addToCart(Product product)
    {
        shoppingCart.addProduct(product);
        return shoppingCart;
    }

    public ArrayList<WishList> viewAllWishLists()
    {
        return wishList;
    }
    
    public Cart viewWishList(int wishListIndex)
    {
        return wishList.get(wishListIndex);
    }

    public Order createOrder(Cart cart)
    {
        return null;
    }

    public Cart viewShoppingCart() 
    {
        return shoppingCart;
    }
}
