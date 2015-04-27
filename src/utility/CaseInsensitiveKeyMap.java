/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lucas
 */
public class CaseInsensitiveKeyMap<T> extends HashMap<String, T>
{
    @Override
    public T put(String key, T value)
    {
        return super.put(key.toLowerCase(), value);
    }

    public T get(String key)
    {
        return super.get(key.toLowerCase());
    }
}
