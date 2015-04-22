package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public class Product
{
    private String model;
    private double price;

    public Product(String model, double price)
    {
        this.model = model;
        this.price = price;
    }

    @Override
    public int hashCode()
    {
        return model.hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }

        if (!(other instanceof Product))
        {
            return false;
        }

        Product p = (Product)other;

        return p.model.equals(this.model) && p.price == this.price;
    }
}
