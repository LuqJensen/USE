package unifiedshoppingexperience;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.DatabaseConnection;

/**
 *
 * @author Gruppe12
 */
public class WishList extends Cart
{
    private String name;

    /**
     * Creates a wish list by creating a cart with a name.
     *
     * @param name The name of the wish list.
     */
    public WishList(String name)
    {
        super();
        this.name = name;
    }

    public WishList(DatabaseConnection db, ResultSet rs) throws SQLException
    {
        super(db, rs);
        ResultSet rs1 = db.Select("SELECT name FROM wishlist WHERE id = ?;", rs.getInt(2));
        if (rs1.next())
        {
            this.name = rs1.getString(2);
        }
    }

    public void save(DatabaseConnection db, PreparedStatement wishlistInsert, PreparedStatement wishlistUpdate, PreparedStatement cartInsert, PreparedStatement cartUpdate) throws SQLException
    {
        super.save(db, cartInsert, cartUpdate);

        wishlistUpdate.setString(1, name);
        wishlistUpdate.setInt(2, cartID);
        wishlistUpdate.addBatch();

        wishlistInsert.setInt(1, cartID);
        wishlistInsert.setString(2, name);
        wishlistInsert.addBatch();
    }
}
