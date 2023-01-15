package com.terbah.sketch.api;

/**
 * @author Dorian TERBAH
 *
 * Interface used to serialize something in JSON.
 *
 * @version 1.0
 */
public interface JSONSerializable
{
    /**
     * @return A JSON string.
     */
    String toJSON();
}
