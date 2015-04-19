package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.List;

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
    protected List<ProductLine> productLines;
    
    static
    {
        cartCreations = 0;
    }

    public Cart()
    {
        this.cartID = cartCreations;
        ++cartCreations;
        this.productLines = new ArrayList<>();
    }

    public Cart addProduct(Product product)
    {
        boolean found = false;
        for (ProductLine pl : productLines)
        {
            if(pl.getProduct().equals(product))
            {
               pl.incrementQuantity(); 
               found=true;
               break;
            }
                       
        }
        if(!found)
        {
            ProductLine productLine = new ProductLine(1, product);
            productLines.add(productLine);
        }
        
        return this;
    }
}
