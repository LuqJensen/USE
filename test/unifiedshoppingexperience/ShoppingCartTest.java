package unifiedshoppingexperience;

import interfaces.ProductLineDTO;
import shared.TestData;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Contains unit tests of the ShoppingCart class. System.out.println loops in
 * each of the testAddProductX methods print the hashCode of each object for
 * visualization, it has no influence on the assertEquals. The outcome of the
 * assertEquals is solely defined by the implementation of equals in
 * ProductLine.
 *
 * @author Gruppe12
 */
public class ShoppingCartTest
{
    public ShoppingCartTest()
    {
    }

    /**
     * Test 1 of addProduct method, of class ShoppingCart. Adds product p1 to
     * cart once, expected result is p1 with quantity 1.
     */
    @Test
    public void testAddProduct1()
    {
        System.out.println("addProduct 1");

        List<ProductLineDTO> expResult = new ArrayList();
        expResult.add(new ProductLine(TestData.p1, 1));

        ShoppingCart instance = new ShoppingCart();
        instance.addProduct(TestData.p1);
        List<ProductLineDTO> result = instance.getProducts();

        for (ProductLineDTO pl : expResult)
        {
            System.out.println(pl);
        }
        for (ProductLineDTO pl : result)
        {
            System.out.println(pl);
        }
        assertEquals(expResult, result);
    }

    /**
     * Test 2 of addProduct method, of class ShoppingCart. Adds product p1 and
     * p2 to cart once, expected result is p2 and p1 with quantity 1.
     */
    @Test
    public void testAddProducts2()
    {
        System.out.println("addProduct 2");

        List<ProductLineDTO> expResult = new ArrayList();
        // We have no control over order of products, as Cart uses a HashMap.
        expResult.add(new ProductLine(TestData.p2, 1));
        expResult.add(new ProductLine(TestData.p1, 1));

        ShoppingCart instance = new ShoppingCart();
        instance.addProduct(TestData.p1);
        instance.addProduct(TestData.p2);
        List<ProductLineDTO> result = instance.getProducts();

        for (ProductLineDTO pl : expResult)
        {
            System.out.println(pl);
        }
        for (ProductLineDTO pl : result)
        {
            System.out.println(pl);
        }
        assertEquals(expResult, result);
    }

    /**
     * Test 3 of addProduct method, of class ShoppingCart. Adds product p1
     * twice, expected result is p1 with quantity 2.
     */
    @Test
    public void testAddProducts3()
    {
        System.out.println("addProduct 3");

        ProductLine pLine = new ProductLine(TestData.p1, 1);
        pLine.addQuantity(1);
        List<ProductLineDTO> expResult = new ArrayList();
        expResult.add(pLine);

        ShoppingCart instance = new ShoppingCart();
        instance.addProduct(TestData.p1);
        instance.addProduct(TestData.p1);
        List<ProductLineDTO> result = instance.getProducts();

        for (ProductLineDTO pl : expResult)
        {
            System.out.println(pl);
        }
        for (ProductLineDTO pl : result)
        {
            System.out.println(pl);
        }
        assertEquals(expResult, result);
    }
}
