package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public abstract class Cart
{

    protected double price;
    protected int productQuantity;
    protected int totalQuantity;

    public Cart(double price, int productQuantity, int totalQuantity)
    {
        this.price = price;
        this.productQuantity = productQuantity;
        this.totalQuantity = totalQuantity;
    }

    public void addProduct(String product)
    {

    }

    public void showProducts()
    {

    }

}
