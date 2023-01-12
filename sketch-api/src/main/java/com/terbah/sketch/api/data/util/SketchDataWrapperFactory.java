package com.terbah.sketch.api.data.util;

/**
 * @author Dorian TERBAH
 *
 * This class will create data wrapper to be used by the components.
 *
 * @since 1.0
 */
public class SketchDataWrapperFactory
{
    /**
     * Create a wrapper for a specific type of data.
     * @param dataClass The type of data wanted te be wrapped.
     * @param <T> The type of data.
     * @return A wrapper with the data class.
     */
    public static <T> SketchDataWrapper<T> getWrapper(Class<T> dataClass)
    {
        return new DefaultSketchDataWrapper<>();
    }

    /**
     * Create a wrapper with a data
     * @param data The corresponding data
     * @return A wrapper with the data
     * @param <T> The type of data
     */
    public static <T> SketchDataWrapper<T> getWrapper(T data) {
        return new DefaultSketchDataWrapper<>(data);
    }
}