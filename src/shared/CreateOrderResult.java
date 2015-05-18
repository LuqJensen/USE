/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import shared.CreateOrderErrors;

/**
 *
 * @author Lucas
 */
public class CreateOrderResult
{
    private CreateOrderErrors error;
    private int orderID;

    public CreateOrderResult(CreateOrderErrors error)
    {
        this.error = error;
        this.orderID = 0;
    }

    public CreateOrderResult(CreateOrderErrors error, int orderID)
    {
        this.error = error;
        this.orderID = orderID;
    }

    public int getOrderID()
    {
        return orderID;
    }

    public CreateOrderErrors getError()
    {
        return error;
    }
}
