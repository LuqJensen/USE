package unifiedshoppingexperience;

import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.DatabaseConnection;

/**
 * Contains appropriate methods for handling Shopping cart related actions such
 * as adding a product.
 *
 * @author Gruppe12
 */
public class ShoppingCart extends Cart
{
    /**
     * Creates a ShoppingCart same way a Cart is created.
     */
    public ShoppingCart()
    {
        super();
    }

    public ShoppingCart(DatabaseConnection db, ResultSet rs) throws SQLException
    {
        super(db, rs);
    }

    /**
     * Adds a product to the shopping cart.
     *
     * @param product The product to be added.
     */
    public void addProduct(Product product)
    {
        ProductLine productLine = productLines.get(product);
        if (productLine != null)
        {
            productLine.incrementQuantity();
        }
        else
        {
            productLines.put(product, new ProductLine(product));
        }

        // BigDecimal is immutable, must assign return value of method to the variable,
        // if not, the calculation is lost.
        price = price.add(product.getPrice());
    }
}
