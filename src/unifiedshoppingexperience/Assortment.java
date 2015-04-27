package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A collection of products provides methods to handle these products.
 *
 * @author Gruppe12
 */
public class Assortment
{
    private Map<String, Set<Product>> typeMap;
    private Map<String, Set<Product>> descriptionMap;
    private Map<String, Product> products;

    /**
     * Creates an assortment for testing purposes.
     *
     * @param products TODO @Ploug
     * @param typeMap The type map with keys pointing to a set of products,
     * based on type of product. For example the key "graphic card" could point
     * to a set of graphic cards.
     * @param descriptionMap The description map with keys pointing to a set of
     * products, based on how the product is described. For example the key
     * "980" would point to a set of products containing "980" in it's
     * description or title.
     */
    public Assortment(Map<String, Product> products, Map<String, Set<Product>> typeMap, Map<String, Set<Product>> descriptionMap)
    {
        if (products == null || typeMap == null || descriptionMap == null)
        {
            throw new NullPointerException();
        }

        this.products = products;
        this.typeMap = typeMap;
        this.descriptionMap = descriptionMap;
    }

    /**
     * A method for getting a list of products based on the type and description
     * tags. The list is sorted by relevance based on the description tags, and
     * filtered based on the type tags.
     *
     * @param descriptionTags The type map with keys pointing to a set of
     * products, based on type of product. For example the key "graphic card"
     * could point to a set of graphic cards.
     * @param typeTags The description map with keys pointing to a set of
     * products, based on how the product is described. For example the key
     * "980" would point to a set of products containing "980" in it's
     * description or title.
     * @return Returns a list of products sorted by relevance based on
     * description tags and filtered by type tags.
     */
    public List<Product> findProducts(String[] descriptionTags, String[] typeTags)
    {
        List<Product> retval = new ArrayList();

        Map<Product, Integer> productMap = new HashMap();

        for (String typeTag : typeTags)
        {
            Set<Product> productSet = typeMap.get(typeTag);

            if (productSet == null)
            {
                continue;
            }

            for (Product product : productSet)
            {
                productMap.put(product, 0);
            }
        }

        // If productMap is empty at this point, type filtering was not performed
        // due to empty typeTags parameter, or empty String elements in typeTags.
        // The need for this, rather than typeTags.length != 0, was discovered during unit testing.
        boolean filteredByType = !productMap.isEmpty();

        for (String descriptionTag : descriptionTags)
        {
            Set<Product> productSet = descriptionMap.get(descriptionTag);

            if (productSet == null)
            {
                continue;
            }

            for (Product product : productSet)
            {
                Integer productHits = productMap.get(product);

                
                if (productHits == null && !filteredByType)
                {
                    productMap.put(product, 1);
                }
                else if (productHits != null)
                {
                    productMap.put(product, productHits + 1);
                }
            }
        }

        // Temporary hack for easy sorting by relevance (productHits)...
        List<ProductHits> temp = new ArrayList();

        for (Product product : productMap.keySet())
        {
            temp.add(new ProductHits(product, productMap.get(product)));
        }

        Collections.sort(temp);

        for (ProductHits ph : temp)
        {
            retval.add(ph.getProduct());
        }

        return retval;
    }

    /**
     * Gets a product based on its model.
     *
     * @param productModel The model of the product.
     * @return Returns a product based on its model.
     */
    public Product getProduct(String productModel)
    {
        return products.get(productModel);
    }
}
