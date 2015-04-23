package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public class Product
{
    private String model;
    private double price;
    private String type;

    public Product(String model, double price, String type)
    {
        this.model = model;
        this.price = price;
        this.type = type;
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
    
    public String getType()
    {
        return type;
    }
}
