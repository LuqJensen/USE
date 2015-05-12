/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.HashMap;

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

    // note, javas implementation is Map<K, V>.get(Object key)...
    // quite odd having a method break its own class' typesafety...
    @Override
    public T get(Object key)
    {
        return super.get(key.toString().toLowerCase());
    }
}
