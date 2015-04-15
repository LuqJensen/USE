/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public class ProductLine
{
    private int quantity;
    private Product product;

    public ProductLine(int quantity, Product product)
    {
        this.product = product;
        this.quantity = quantity;
    }
    
    public Product getProduct()
    {
        return this.product;
    }
    
    public void incrementQuantity()
    {
        ++quantity;
    }
}
