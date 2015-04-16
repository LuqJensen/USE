package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public class ProductHits
{
    private int hits;
    private final Product product;

    public ProductHits(Product product, int hits)
    {
        this.hits = hits;
        this.product = product;
    }
    
    public int compareTo(ProductHits ph)
    {
        return this.hits - ph.hits;
    }

    public Product getProduct()
    {
        return product;
    }
}
