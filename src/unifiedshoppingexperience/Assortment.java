package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Gruppe12
 */
public class Assortment
{
    private Map<String, Set<Product>> typeMap;
    private Map<String, Set<Product>> descriptionMap;

    public List<Product> findProducts(String[] descriptionTags, String[] typeTags)
    {
        assert(descriptionTags != null && typeTags != null);

        Map<Product, Integer> productMap = new HashMap();

        for (String typeTag : typeTags)
        {
            for (Product product : typeMap.get(typeTag))
            {
                productMap.put(product, 0);
            }
        }

        for (String descriptionTag : descriptionTags)
        {
            for (Product product : descriptionMap.get(descriptionTag))
            {
                Integer productHits = productMap.get(product);

                if (productHits == null && typeTags.length == 0)
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
        Set<ProductHits> temp = new TreeSet();

        for (Product product : productMap.keySet())
        {
            temp.add(new ProductHits(product, productMap.get(product)));
        }

        List<Product> retval = new ArrayList();

        for (ProductHits ph : temp)
        {
            retval.add(ph.getProduct());
        }

        return retval;
    }
}
