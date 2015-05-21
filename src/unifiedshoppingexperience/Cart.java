package unifiedshoppingexperience;

import interfaces.CartDTO;
import interfaces.ProductLineDTO;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import persistence.DatabaseConnection;

/**
 * A cart containing productLines and methods to handle these products. For
 * example adding products to the cart.
 *
 * @author Gruppe12
 */
public abstract class Cart implements CartDTO
{
    protected static int cartCreations;
    protected BigDecimal price;
    //protected int totalQuantity;
    protected int cartID;
    protected Map<Product, ProductLine> productLines;

    static
    {
        cartCreations = 0;
    }

    /**
     * Creates a cart and increments cartCreations for the system to know total
     * amount of carts created.
     */
    public Cart()
    {
        this.cartID = cartCreations + 1;
        this.price = new BigDecimal(0.0);
        this.productLines = new HashMap();
        incrementCartCreations();
    }

    public Cart(DatabaseConnection db, ResultSet rs) throws SQLException
    {
        this.cartID = rs.getInt(2);
        this.price = new BigDecimal(0.0);
        this.productLines = new HashMap();
        ResultSet rs1 = db.Select("SELECT product_model, quantity FROM productline WHERE cart_id = ?;", this.cartID);
        while (rs1.next())
        {
            ResultSet rs2 = db.Select("SELECT * FROM product WHERE model = ?", rs1.getString(1));
            if (rs2.next())
            {
                Product product = new Product(rs2.getString(1), rs2.getBigDecimal(2), rs2.getString(3), rs2.getString(4));
                this.productLines.put(product, new ProductLine(product, rs1.getInt(2)));
                // BigDecimal is immutable, must reassign its value after calculation or the calculation is lost...
                this.price = this.price.add(product.getPrice());
            }
        }

        incrementCartCreations();
    }

    public void save(DatabaseConnection db, PreparedStatement cartInsert, PreparedStatement cartUpdate) throws SQLException
    {
        cartUpdate.setInt(2, cartID);
        cartUpdate.addBatch();
        cartInsert.setInt(2, cartID);
        cartInsert.setInt(3, cartID);
        cartInsert.addBatch();

        String query2 = "UPDATE productline SET product_model = ?, quantity = ? WHERE cart_id = ?;";
        String query = "INSERT INTO productline(cart_id, product_model, quantity) SELECT ?, ?, ? WHERE NOT EXISTS(SELECT * FROM productline WHERE cart_id = ? AND product_model = ?);";
        PreparedStatement productlineInsert = db.createPreparedStatement(query);
        PreparedStatement productlineUpdate = db.createPreparedStatement(query2);

        // Saving products to DB is done in assortment.
        for (ProductLine pl : productLines.values())
        {
            productlineUpdate.setString(1, pl.getProduct().getModel());
            productlineUpdate.setInt(2, pl.getQuantity());
            productlineUpdate.setInt(3, cartID);
            productlineUpdate.addBatch();

            productlineInsert.setInt(1, cartID);
            productlineInsert.setString(2, pl.getProduct().getModel());
            productlineInsert.setInt(3, pl.getQuantity());
            productlineInsert.setInt(4, cartID);
            productlineInsert.setInt(5, pl.getQuantity());
            productlineInsert.addBatch();
        }
    }

    public int getID()
    {
        return this.cartID;
    }

    /**
     *
     * @return Returns all the product lines in the cart.
     */
    @Override
    public List<ProductLineDTO> getProducts()
    {
        return new ArrayList(productLines.values());
    }

    private static void incrementCartCreations()
    {
        ++cartCreations;
    }

    @Override
    public BigDecimal getPrice()
    {
        return price;
    }
}
