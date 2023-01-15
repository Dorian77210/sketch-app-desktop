package com.terbah.sketch.api.data.dataframe;

import com.terbah.sketch.api.Copyable;

/**
 * @author Dorian TERBAH
 *
 * This class represents a cell inside a dataframe.
 *
 * @since 1.0
 */
public interface DataFrameCell extends Copyable<DataFrameCell>
{
    /**
     * @return The string value associated to this cell.
     */
    String asString();

    /**
     * @return The number value associated to this cell.
     * If the value is not a number, then the value will be null.
     */
    Number asNumber();

    /**
     * @return The raw value associated to this cell.
     */
    Object value();

    /**
     * @return <code>true</code> If its associated value is a String, else <code>false</code>.
     */
    boolean isString();

    /**
     * @return <code>true</code> If its associated value is a Number, else <code>false</code>.
     */
    boolean isNumber();
}
