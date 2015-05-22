/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

/**
 *
 * @author Lucas
 */
public class DataStore
{
    private static IRepository repo = null;

    public static IRepository getPersistence()
    {
        if (repo == null)
        {
            repo = new DataMapping();
        }
        return repo;
        //return repo == null ? repo = new DataMapping() : repo;
    }
}
