package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Gruppe12
 */
public class AssortmentTest
{
    private static Set<Product> productSet1;
    private static Set<Product> productSet2;
    private static Map<String, Set<Product>> typeMap;
    private static Map<String, Set<Product>> descriptionMap;

    public AssortmentTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        productSet1 = new HashSet();
        productSet1.add(ProductHelper.p1);
        productSet1.add(ProductHelper.p2);

        productSet2 = new HashSet();
        productSet2.add(ProductHelper.p1);
        productSet2.add(ProductHelper.p3);

        typeMap = new HashMap();
        typeMap.put("Grafikkort", productSet2);

        descriptionMap = new HashMap();
        descriptionMap.put("970", productSet1);
        descriptionMap.put("Nvidia", productSet2);
    }

    /**
     * Test of findProducts method, of class Assortment.
     */
    @Test
    public void testFindNoProducts()
    {
        System.out.println("findProducts with empty parameter arrays.");

        String[] descriptionTags = "".split(" ");
        String[] typeTags = new String[0];

        Assortment instance = new Assortment(typeMap, descriptionMap);
        List<Product> expResult = new ArrayList();
        List<Product> result = instance.findProducts(descriptionTags, typeTags);

        assertEquals(expResult, result);
    }

    @Test
    public void testFindProducts1()
    {
        System.out.println("findProducts 1");

        String[] descriptionTags = "Nvidia 970".split(" ");
        String[] typeTags = new String[] {"Grafikkort"};

        Assortment instance = new Assortment(typeMap, descriptionMap);

        List<Product> expResult = new ArrayList();
        expResult.add(ProductHelper.p1);
        expResult.add(ProductHelper.p3);
        List<Product> result = instance.findProducts(descriptionTags, typeTags);

        for (Product p : expResult)
        {
            System.out.println(p);
        }
        for (Product p : result)
        {
            System.out.println(p);
        }

        assertEquals(expResult, result);
    }

    @Test
    public void testFindProducts2()
    {
        System.out.println("findProducts 2");

        String[] descriptionTags = "970".split(" ");
        String[] typeTags = new String[] {"Grafikkort"};

        Assortment instance = new Assortment(typeMap, descriptionMap);

        List<Product> expResult = new ArrayList();
        expResult.add(ProductHelper.p1);
        expResult.add(ProductHelper.p3);
        List<Product> result = instance.findProducts(descriptionTags, typeTags);

        for (Product p : expResult)
        {
            System.out.println(p);
        }
        for (Product p : result)
        {
            System.out.println(p);
        }

        assertEquals(expResult, result);
    }

    @Test
    public void testFindProducts3()
    {
        System.out.println("findProducts 3");

        String[] descriptionTags = "970".split(" ");
        String[] typeTags = new String[0];

        Assortment instance = new Assortment(typeMap, descriptionMap);

        List<Product> expResult = new ArrayList();
        expResult.add(ProductHelper.p1);
        expResult.add(ProductHelper.p2);
        List<Product> result = instance.findProducts(descriptionTags, typeTags);

        for (Product p : expResult)
        {
            System.out.println(p);
        }
        for (Product p : result)
        {
            System.out.println(p);
        }

        assertEquals(expResult, result);
    }
}
