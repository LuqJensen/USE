package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public abstract class Cart
{
    protected static int cartCreations;
    protected double price;
    protected int totalQuantity;
    protected int cartID;

    public Cart()
    {
        this.cartID = cartCreations;
        ++cartCreations;
    }

    public void addProduct(Product product)
    {

    }

    public void showProducts()
    {

    }

}
