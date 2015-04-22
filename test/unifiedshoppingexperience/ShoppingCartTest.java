package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * System.out.println loops in each of the testAddProductX methods print the
 * hashCode of each object for visualization, it has no influence on the
 * assertEquals. The outcome of the assertEquals is solely defined by the
 * implementation of equals in ProductLine.
 *
 * @author Gruppe12
 */
public class ShoppingCartTest
{
    public ShoppingCartTest()
    {
    }

    /**
     * Test of addProduct method, of class ShoppingCart.
     */
    @Test
    public void testAddProduct1()
    {
        System.out.println("addProduct 1");

        List<ProductLine> expResult = new ArrayList();
        expResult.add(new ProductLine(ProductHelper.p1));

        ShoppingCart instance = new ShoppingCart();
        Cart cart = instance.addProduct(ProductHelper.p1);
        List<ProductLine> result = cart.getProducts();

        for (ProductLine pl : expResult)
        {
            System.out.println(pl);
        }
        for (ProductLine pl : result)
        {
            System.out.println(pl);
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testAddProducts2()
    {
        System.out.println("addProduct 2");

        List<ProductLine> expResult = new ArrayList();
        // We have no control over order of products, as Cart uses a HashMap.
        expResult.add(new ProductLine(ProductHelper.p2));
        expResult.add(new ProductLine(ProductHelper.p1));

        ShoppingCart instance = new ShoppingCart();
        Cart cart = instance.addProduct(ProductHelper.p1);
        instance.addProduct(ProductHelper.p2);
        List<ProductLine> result = cart.getProducts();

        for (ProductLine pl : expResult)
        {
            System.out.println(pl);
        }
        for (ProductLine pl : result)
        {
            System.out.println(pl);
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testAddProducts3()
    {
        System.out.println("addProduct 3");

        ProductLine pLine = new ProductLine(ProductHelper.p1);
        pLine.incrementQuantity();
        List<ProductLine> expResult = new ArrayList();
        expResult.add(pLine);

        ShoppingCart instance = new ShoppingCart();
        Cart cart = instance.addProduct(ProductHelper.p1);
        instance.addProduct(ProductHelper.p1);
        List<ProductLine> result = cart.getProducts();

        for (ProductLine pl : expResult)
        {
            System.out.println(pl);
        }
        for (ProductLine pl : result)
        {
            System.out.println(pl);
        }
        assertEquals(expResult, result);
    }
}
