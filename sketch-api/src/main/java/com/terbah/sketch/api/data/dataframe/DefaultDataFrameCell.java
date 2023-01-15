package com.terbah.sketch.api.data.dataframe;

import com.terbah.sketch.api.data.util.Utils;

/**
 * @author Dorian TERBAH
 *
 * This is the default implementation of the interface DataFrameCell.
 *
 * @since 1.0
 * @see DataFrameCell
 */
class DefaultDataFrameCell implements DataFrameCell
{
    /**
     * Value associated to this cell.
     */
    private final Object value;

    /**
     * Boolean to know if the cell contains numeric value.
     */
    private final boolean isNumeric;

    /**
     * Create a new dataframe cell.
     * @param value The value associated to this cell.
     */
    public DefaultDataFrameCell(Object value)
    {
        this.value = value;
        this.isNumeric = Utils.isNumber(value.toString());
    }

    @Override
    public String asString()
    {
        return this.value.toString();
    }

    @Override
    public Number asNumber()
    {
        return this.isNumeric ? (Number) this.value : null;
    }

    @Override
    public Object value()
    {
        return this.value;
    }

    @Override
    public boolean isString()
    {
        return !this.isNumeric;
    }

    @Override
    public boolean isNumber()
    {
        return this.isNumeric;
    }

    private Object createValueCopy()
    {
        return this.isNumeric ? Double.parseDouble(this.value.toString()) : this.value.toString();
    }

    @Override
    public DataFrameCell copy()
    {
        return new DefaultDataFrameCell(this.createValueCopy());
    }
}
