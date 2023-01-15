package com.terbah.sketch.api.data.dataframe;

import com.terbah.sketch.api.Copyable;
import com.terbah.sketch.api.JSONSerializable;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Dorian TERBAH
 *
 * This class represents a column of a dataframe.
 *
 * @since 1.0
 */
public interface DataFrameColumn extends Copyable<DataFrameColumn>
{
    /**
     * @author Dorian TERBAH
     *
     * This enumeration permits to know the data's type for a column.
     *
     * @since 1.0
     */
    enum DataFrameType
    {
        /**
         * Numeric values
         */
        NUMERIC,

        /**
         * String values
         */
        STRING
    }
    /**
     * @return The number of cells contained in this column.
     */
    int size();

    /**
     * Return the cell associated to an index.
     * @param index The index.
     * @return The associated cell.
     * @throws IndexOutOfBoundsException if the index is inferior to 0 or if the index is greater than the column's size.
     */
    DataFrameCell at(int index);

    /**
     * Remove an item according to its position in the column.
     * @param index The index.
     * @throws IndexOutOfBoundsException if the index is inferior to 0 or if the index is greater than the column's size.
     */
    void removeAt(int index);

    /**
     * Remove all occurrences of item inside the column.
     * @param item The item to remove.
     */
    void removeAll(Object item);

    /**
     * Add a String value inside the column.
     * @param value The String to add.
     */
    void add(String value);

    /**
     * Add a number inside the column.
     * @param value The number to add.
     */
    void add(Number value);

    /**
     * For each method to loop over the cells in the column.
     * @param action The function passed to proceed the cells
     * over the iterations.
     */
    default void forEach(Consumer<? super DataFrameCell> action)
    {
        for (int index = 0; index < this.count(); ++index)
        {
            DataFrameCell cell = this.at(index);
            action.accept(cell);
        }
    }

    /**
     * @return The name associated to the column.
     */
    String getName();

    /**
     * Swap to items inside the columns.
     * @param sourceIndex The source index associated to the first item to swap.
     * @param targetIndex The target index associated to the target item to swap.
     * @throws IndexOutOfBoundsException if the source index or the target index
     * is inferior to 0 or if it is greater than the column's size.
     */
    void swap(int sourceIndex, int targetIndex);

    /**
     * @return <code>true</code> If the column contains only numeric values, else <code>false</code>.
     */
    boolean isNumericColumn();

    /**
     * @return <code>true</code> If the column contains only String values, else <code>false</code>.
     */
    boolean isStringColumn();

    /**
     * @return The average of the data.
     * @throws IllegalOperationException If the column doesn't contain numeric values.
     */
    Number avg();

    /**
     * @return The sum of the data.
     * @throws IllegalOperationException If the column doesn't contain numeric values.
     */
    Number sum();

    /**
     * @return The maximum of the data.
     * @throws IllegalOperationException If the column doesn't contain numeric values.
     */
    Number max();

    /**
     * @return The minimum of the data.
     * @throws IllegalOperationException If the column doesn't contain numeric values.
     */
    Number min();

    /**
     * Normalize the column.
     * @throws IllegalOperationException If the column doesn't contain numeric values.
     */
    void normalize();

    /**
     * Compute the absolute value for each cell.
     * @throws IllegalOperationException If the column doesn't contain numeric values.
     */
    void abs();

    /**
     * @return The number of cells presented in the column.
     */
    int count();

    /**
     * @return The values stored in the column.
     */
    List<Object> values();
}
