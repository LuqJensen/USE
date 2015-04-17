package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppe12
 */
public class AssortmentTest
{
    Product p1, p2, p3;
    Set<Product> productSet1;
    Set<Product> productSet2;
    Map<String, Set<Product>> typeMap;
    Map<String, Set<Product>> descriptionMap;

    public AssortmentTest()
    {
        p1 = new Product("NV970", 0.0);
        p2 = new Product("AX970", 0.0);
        p3 = new Product("NV660", 0.0);

        productSet1 = new HashSet();
        productSet1.add(p1);
        productSet1.add(p2);

        productSet2 = new HashSet();
        productSet2.add(p1);
        productSet2.add(p3);

        typeMap = new HashMap<String, Set<Product>>();
        typeMap.put("Grafikkort", productSet2);

        descriptionMap = new HashMap<String, Set<Product>>();
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
        String[] typeTags = "".split(" ");

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
        String[] typeTags = "Grafikkort".split(" ");

        Assortment instance = new Assortment(typeMap, descriptionMap);

        List<Product> expResult = new ArrayList();
        expResult.add(p1);
        expResult.add(p3);
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
        String[] typeTags = "Grafikkort".split(" ");

        Assortment instance = new Assortment(typeMap, descriptionMap);

        List<Product> expResult = new ArrayList();
        expResult.add(p1);
        expResult.add(p3);
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
        String[] typeTags = "".split(" ");

        Assortment instance = new Assortment(typeMap, descriptionMap);

        List<Product> expResult = new ArrayList();
        expResult.add(p1);
        expResult.add(p2);
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
