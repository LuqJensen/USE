package unifiedshoppingexperience;

import shared.TestData;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import utility.CaseInsensitiveKeyMap;

/**
 * Contains set up and unit tests of the Assortment class.
 *
 * @author Gruppe12
 */
public class AssortmentTest
{
    private static Assortment instance;

    public AssortmentTest()
    {
    }

    /**
     * Static one time setup for all @Tests
     */
    @BeforeClass
    public static void setUpClass()
    {
        Set<Product> productSet1 = new HashSet();
        productSet1.add(TestData.p1);
        productSet1.add(TestData.p2);

        Set<Product> productSet2 = new HashSet();
        productSet2.add(TestData.p1);
        productSet2.add(TestData.p3);

        Map<String, Set<Product>> typeMap = new CaseInsensitiveKeyMap();
        typeMap.put("Grafikkort", productSet2);

        Map<String, Set<Product>> descriptionMap = new CaseInsensitiveKeyMap();
        descriptionMap.put("970", productSet1);
        descriptionMap.put("Nvidia", productSet2);

        Map<String, Product> productMap = new CaseInsensitiveKeyMap();
        productMap.put("NV970", TestData.p1);
        productMap.put("AX970", TestData.p2);
        productMap.put("NV660", TestData.p3);

        instance = new Assortment(productMap, typeMap, descriptionMap);
    }

    /**
     * Test 1 of findProducts method, of class Assortment. Given no description
     * tags or type tags, expected result is empty.
     */
    @Test
    public void testFindProducts1()
    {
        System.out.println("findProducts 1");

        String[] descriptionTags = "".split(" ");
        String[] typeTags = new String[0];

        List<Product> expResult = new ArrayList();
        List<Product> result = instance.findProducts(descriptionTags, typeTags);

        assertEquals(expResult, result);
    }

    /**
     * Test 2 of findProducts method, of class Assortment. Given 2 description
     * tags and 1 type tag, expected result is product p1 and p3.
     */
    @Test
    public void testFindProducts2()
    {
        System.out.println("findProducts 2");

        String[] descriptionTags = "Nvidia 970".split(" ");
        String[] typeTags = new String[]
        {
            "Grafikkort"
        };

        List<Product> expResult = new ArrayList();
        expResult.add(TestData.p1);
        expResult.add(TestData.p3);
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

    /**
     * Test 3 of findProducts method, of class Assortment. Given 1 description
     * tag and 1 type tag, expected result is product p1 and p3.
     */
    @Test
    public void testFindProducts3()
    {
        System.out.println("findProducts 3");

        String[] descriptionTags = "970".split(" ");
        String[] typeTags = new String[]
        {
            "Grafikkort"
        };

        List<Product> expResult = new ArrayList();
        expResult.add(TestData.p1);
        expResult.add(TestData.p3);
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

    /**
     * Test 4 of findProducts method, of class Assortment. Given 1 description
     * tag and no type tag, expected result is product p2 and p1.
     */
    @Test
    public void testFindProducts4()
    {
        System.out.println("findProducts 4");

        String[] descriptionTags = "970".split(" ");
        String[] typeTags = new String[0];

        List<Product> expResult = new ArrayList();
        expResult.add(TestData.p2);
        expResult.add(TestData.p1);
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
    public void testGetProduct()
    {
        assertEquals(instance.getProduct("NV970"), TestData.p1);
    }
}
