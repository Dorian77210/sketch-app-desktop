package com.terbah.sketch.api.data.dataframe;

/**
 * @author Dorian TERBAH
 *
 * This exception will be thrown when a CSV file is malformed.
 *
 * @since 1.0
 */
public class MalformedCSVException extends RuntimeException
{
    /**
     * Create a new MalformedCSVException.
     * @param message The message associated to the exception.
     */
    public MalformedCSVException(String message)
    {
        super(message);
    }
}
