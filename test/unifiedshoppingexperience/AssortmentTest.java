package unifiedshoppingexperience;

import interfaces.ProductDTO;
import shared.TestData;
import java.util.ArrayList;
import java.util.List;
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
        Map<String, Product> productMap = new CaseInsensitiveKeyMap();
        productMap.put("NV970", TestData.p1);
        productMap.put("MSI970", TestData.p2);
        productMap.put("NV660", TestData.p3);
        productMap.put("I7-4770K", TestData.p4);
        productMap.put("I5-4670K", TestData.p5);

        Map<String, Set<Product>> typeMap = new CaseInsensitiveKeyMap();
        for (Product p : productMap.values())
        {
            Set<Product> sp = typeMap.get(p.getType());

            if (sp == null)
            {
                sp = new HashSet();
                typeMap.put(p.getType(), sp);
            }

            sp.add(productMap.get(p.getModel()));
        }

        Map<String, Set<Product>> descriptionMap = new CaseInsensitiveKeyMap();
        for (Product p : productMap.values())
        {
            for (String dTag : (p.getType() + " " + p.getName()).split(" "))
            {
                Set<Product> sp = descriptionMap.get(dTag);
                if (sp == null)
                {
                    sp = new HashSet();
                    descriptionMap.put(dTag, sp);
                }

                sp.add(productMap.get(p.getModel()));
            }
        }

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

        List<ProductDTO> expResult = new ArrayList();
        List<ProductDTO> result = instance.findProducts(descriptionTags, typeTags);

        assertEquals(expResult, result);
    }

    /**
     * Test 2 of findProducts method, of class Assortment. Given 2 description
     * tags and 1 type tag, expected result is product p1, p2, and p3.
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

        List<ProductDTO> expResult = new ArrayList();
        expResult.add(TestData.p1);
        expResult.add(TestData.p2);
        expResult.add(TestData.p3);
        List<ProductDTO> result = instance.findProducts(descriptionTags, typeTags);

        System.out.println("Expected result:");
        for (ProductDTO p : expResult)
        {
            System.out.println(p.getName());
        }

        System.out.println("Result:");
        for (ProductDTO p : result)
        {
            System.out.println(p.getName());
        }

        assertEquals(expResult, result);
    }

    /**
     * Test 3 of findProducts method, of class Assortment. Given 2 description
     * tags and 1 type tag, expected result is product p2 and p1.
     */
    @Test
    public void testFindProducts3()
    {
        System.out.println("findProducts 3");

        String[] descriptionTags = "MSI 970".split(" ");
        String[] typeTags = new String[]
        {
            "Grafikkort"
        };

        List<ProductDTO> expResult = new ArrayList();
        expResult.add(TestData.p2);
        expResult.add(TestData.p1);
        List<ProductDTO> result = instance.findProducts(descriptionTags, typeTags);

        System.out.println("Expected result:");
        for (ProductDTO p : expResult)
        {
            System.out.println(p.getName());
        }

        System.out.println("Result:");
        for (ProductDTO p : result)
        {
            System.out.println(p.getName());
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

        List<ProductDTO> expResult = new ArrayList();
        expResult.add(TestData.p2);
        expResult.add(TestData.p1);
        List<ProductDTO> result = instance.findProducts(descriptionTags, typeTags);

        System.out.println("Expected result:");
        for (ProductDTO p : expResult)
        {
            System.out.println(p.getName());
        }

        System.out.println("Result:");
        for (ProductDTO p : result)
        {
            System.out.println(p.getName());
        }

        assertEquals(expResult, result);
    }

    /**
     * Test 4 of findProducts method, of class Assortment. Given 1 description
     * tag and no type tag, expected result is product p2 and p1.
     */
    @Test
    public void testFindProducts5()
    {
        System.out.println("findProducts 5");

        String[] descriptionTags = "GTX".split(" ");
        String[] typeTags = new String[]
        {
            "Grafikkort", "Processorer"
        };

        List<ProductDTO> expResult = new ArrayList();
        expResult.add(TestData.p2);
        expResult.add(TestData.p1);
        expResult.add(TestData.p3);
        List<ProductDTO> result = instance.findProducts(descriptionTags, typeTags);

        System.out.println("Expected result:");
        for (ProductDTO p : expResult)
        {
            System.out.println(p.getName());
        }

        System.out.println("Result:");
        for (ProductDTO p : result)
        {
            System.out.println(p.getName());
        }

        assertEquals(expResult, result);
    }

    @Test
    public void testGetProduct1()
    {
        assertEquals(instance.getProduct("NV970"), TestData.p1);
    }

    @Test
    public void testGetProduct2()
    {
        assertEquals(instance.getProduct("NV97"), null);
    }
}
