package com.terbah.sketch.api.data.util;

/**
 * @author Dorian TERBAH
 *
 * Implementation of the interface SketchDataWrapper.
 *
 * @param <T> The type of the data.
 * @see SketchDataWrapper
 * @version 1.0
 */
class DefaultSketchDataWrapper<T> implements SketchDataWrapper<T>
{
    private T data;

    /**
     * Default constructor.
     */
    DefaultSketchDataWrapper()
    {
        this.data = null;
    }

    /**
     * Create a wrapper with the associated data.
     * @param data The associated data.
     */
    DefaultSketchDataWrapper(final T data)
    {
        this.data = data;
    }

    @Override
    public void setData(T data)
    {
        this.data = data;
    }

    @Override
    public T getData()
    {
        return this.data;
    }

    @Override
    public boolean isDataAvailable()
    {
        return this.data != null;
    }
}