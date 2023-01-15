package com.terbah.sketch.api.data.dataframe;

/**
 * @author Dorian TERBAH
 *
 * This exception will be thrown when the creation
 * of dataframe with a given JSON data met an error.
 *
 * @since 1.0
 */
public class MalformedJSONDataFrameException extends RuntimeException
{
    /**
     * Create a new MalformedJSONDataFrame.
     * @param message The associated message to the exception.
     */
    public MalformedJSONDataFrameException(String message)
    {
        super(message);
    }
}
