package com.terbah.sketch.api.data.util;

/**
 * @author Dorian TERBAH
 *
 * This class provides somue utils methods.
 *
 * @since 1.0
 * @version 1.0
 */
public final class Utils
{
    /**
     * Check if a String is a number or not.
     * @param str The string.
     * @return <code>true</code> If the str is a number, else <code>false</code>.
     */
    public static boolean isNumber(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
