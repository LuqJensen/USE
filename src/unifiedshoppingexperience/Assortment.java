package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Gruppe12
 */
public class Assortment
{
    private Map<String, Set<Product>> typeMap;
    private Map<String, Set<Product>> descriptionMap;

    // denne constructor er lavet til at teste Assortment med AssortmentTest.java (jUnit tests)
    public Assortment(Map<String, Set<Product>> typeMap, Map<String, Set<Product>> descriptionMap)
    {
        assert(typeMap != null && descriptionMap != null);
        this.typeMap = typeMap;
        this.descriptionMap = descriptionMap;
    }

    public List<Product> findProducts(String[] descriptionTags, String[] typeTags)
    {
        List<Product> retval = new ArrayList();

        Map<Product, Integer> productMap = new HashMap();

        for (String typeTag : typeTags)
        {
            Set<Product> productSet = typeMap.get(typeTag);

            if (productSet == null)
                continue;

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
                continue;

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
}
