package com.terbah.sketch.api.data.dataframe;

/**
 * @author Dorian TERBAH
 *
 * This exception will be used if there is an operation
 * computed on an invalid column data.
 *
 * @since 1.0
 */
public class IllegalOperationException extends RuntimeException
{
    public IllegalOperationException(String message)
    {
        super(message);
    }
}
