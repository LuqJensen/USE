package unifiedshoppingexperience;

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
}
