package unifiedshoppingexperience;

/**
 * Keeps information about a product, and how many hits it has. Hits is the
 * amount description tags that fits the product.
 * It implements comparable and overrides the compareTo method so its compared
 * based on the amount of hits the product has.
 * This makes it easy to sort a collection of instances of this class, based on
 * hits (relevancy).
 *
 *
 * @author Gruppe12
 */
public class ProductHits implements Comparable<ProductHits>
{
    private int hits;
    private final Product product;

    /**
     * Creates a productHits with a product and its amount of hits.
     *
     * @param product The product.
     * @param hits The amount of description tags that fits the product.
     */
    public ProductHits(Product product, int hits)
    {
        this.hits = hits;
        this.product = product;
    }

    /**
     * Compares 2 productHits based on hits.
     *
     * @param ph The object to which it is compared with.
     * @return Returns the difference (also negative) between the 2 compared
     * objects.
     */
    @Override
    public int compareTo(ProductHits ph)
    {
        // sort descending instead of ascending
        return ph.hits - this.hits;
    }

    /**
     * Gets the product the object is counting hits for.
     *
     * @return Returns the product the object is counting hits for.
     */
    public Product getProduct()
    {
        return product;
    }
}
