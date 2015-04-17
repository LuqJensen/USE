package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public class ProductHits implements Comparable<ProductHits>
{
    private int hits;
    private final Product product;

    public ProductHits(Product product, int hits)
    {
        this.hits = hits;
        this.product = product;
    }
    
    @Override
    public int compareTo(ProductHits ph)
    {
        // sort descending instead of ascending
        return ph.hits - this.hits;
    }

    public Product getProduct()
    {
        return product;
    }
}
