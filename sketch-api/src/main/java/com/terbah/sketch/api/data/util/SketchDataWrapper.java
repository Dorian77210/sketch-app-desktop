package com.terbah.sketch.api.data.util;

/**
 * @author Dorian TERBAH
 *
 * Class used to store specific data that might not been initialized directly during the construction of
 * a component.
 *
 * @param <T> The type of the data.
 * @version 1.0
 */
public interface SketchDataWrapper<T>
{
    /**
     * Update the value of the data.
     * @param data The enw value for the data.
     */
    void setData(T data);

    /**
     * @return The associated data to this wrapper.
     */
    T getData();

    /**
     * @return <code>true</code> if the data is not null, else <code>false</code>.
     */
    boolean isDataAvailable();
}