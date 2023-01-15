package com.terbah.sketch.api;

/***
 * @author Dorian TERBAH
 * @param <T> The type of the implemented class.
 * This interface defines a method to make a copy of an object.
 * @since 1.0
 */
public interface Copyable<T>
{
    /**
     * @return The copy of the object.
     */
    T copy();
}
