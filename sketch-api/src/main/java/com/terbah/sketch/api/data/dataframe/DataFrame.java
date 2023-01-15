package com.terbah.sketch.api.data.dataframe;

import com.terbah.sketch.api.Copyable;
import com.terbah.sketch.api.JSONSerializable;
import com.terbah.sketch.api.data.map.SketchStringToNumberMap;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Dorian TERBAH
 *
 * This interface will define the methods used by a dataframe.
 *
 * @since 1.0
 */
public interface DataFrame extends Copyable<DataFrame>
{
    /**
     * @return The number of columns inside the dataframe.
     */
    int getColumnsCount();

    /**
     * @return The number of rows inside the dataframe.
     */
    int getRowsCount();

    /**
     * @param column The column to determine if it exists inside the dataframe.
     * @return <code>true</code> if the column exists, else <code>false</code>.
     */
    boolean exists(final String column);

    /**
     * @return The list of headers in the dataframe.
     */
    List<String> getHeaders();

    /**
     * Select some header in a dataframe.
     * @param headers The headers to select.
     * @return The dataframe with the headers in parameters.
     */
    DataFrame select(List<String> headers);

    /**
     * @return True if there is no data available in the dataframe, else false.
     */
    boolean isEmpty();

    /**
     * @return A dataframe with the absolute values for all the data.
     */
    DataFrame abs();

    /**
     * Compute the absolute values for a column.
     * @param column The name of the column
     * @return The dataframe with the absolute column
     * @throws IllegalOperationException If the column doesn't exist.
     * @throws IllegalOperationException If the column doesn't contain
     * number values.
     */
    DataFrame abs(String column);

    /**
     * Compute the average value of a column.
     * @param column The column associated to the computed average.
     * @return The average value.
     * @throws IllegalOperationException If the column doesn't contain
     * number values.
     * @throws IllegalOperationException If the column doesn't exist.
     */
    Number avg(String column);

    /**
     * @return A map with the average values associated to the
     * numeric columns.
     */
    default SketchStringToNumberMap avg()
    {
        SketchStringToNumberMap map = new SketchStringToNumberMap();

        for (String header : this.getHeaders())
        {
            Optional<DataFrameColumn> optional = this.select(header);
            if (optional.isPresent())
            {
                DataFrameColumn column = optional.get();
                if (column.isNumericColumn())
                {
                    map.put(header, column.avg());
                }
            }
        }

        return map;
    }

    /**
     * Compute the sum value of a column.
     * @param column The column associated to the computed sum.
     * @return The sum value.
     * @throws IllegalOperationException If the column doesn't contain
     * numeric values.
     * @throws IllegalOperationException If the column doesn't exist.
     */
    Number sum(String column);

    /**
     * Compute the maximum numeric value of a column.
     * @param column The column associated to the maximum computed.
     * @return The maximum of the column.
     * @throws IllegalOperationException If the column doesn't contain
     * numeric values.
     * @throws IllegalOperationException If the column doesn't exist.
     */
    Number max(String column);

    /**
     * Compute the minimum numeric value of a column.
     * @param column The column associated to the minimum computed.
     * @return The minimum of the column.
     * @throws IllegalOperationException If the column doesn't contain
     * numeric values.
     * @throws IllegalOperationException If the column doesn't exist.
     */
    Number min(String column);

    /**
     * Normalize a column. This column should contain only numeric values to
     * be correctly computed.
     * @param column The column associated the computation.
     * @return The dataframe with the column normalized.
     * @throws IllegalOperationException If the column doesn't contain
     * numeric values or if the column doesn't exist.
     * @throws IllegalOperationException If the column doesn't exist.
     */
    DataFrame normalize(String column);

    /**
     * Normalize all the columns that contains only numeric values.
     * @return The dataframe with the numeric column normalized.
     */
    DataFrame normalize();

    /**
     * Filter the dataframe according to a predicate.
     * @param column The column associated to the predicate.
     * @param predicate The predicate used during the filtering.
     * @return The filtered dataframe.
     */
    DataFrame filter(String column, Predicate<DataFrameCell> predicate);

    /**
     * Create and add a column that contain only String values in the dataframe.
     * If the values size param is greater than the number of rows, all the others
     * columns will be populated with String missing values.
     * Else if the values size is less than the number rows, the new column will be
     * populated with String missing values.
     *
     * @param column The new column name.
     * @param values The String values associated to the new column.
     * @return The dataframe with the new column.
     */
    DataFrame addStringColumn(String column, List<String> values);

    /**
     * Create and add a column that contains only numeric values in the dataframe.
     * If the values size param is greater than the number of rows, all the others
     * columns will be populated with String missing values.
     * Else if the values size is less than the number rows, the new column will be
     * populated with String missing values.
     *
     * @param column The new column name.
     * @param values The numeric values associated to the new column created.
     * @return The dataframe with the new column.
     */
    DataFrame addNumericColumn(String column, List<Number> values);

    /**
     * Remove a column associated to the column's name.
     * @param column The column's name.
     * @return The associated removed column if it exists, else null.
     */
    Optional<DataFrameColumn> remove(String column);

    /**
     * @return A copy of the current dataframe.
     */
    DataFrame copy();

    /**
     * Get a column by its name.
     * @param column The column name.
     * @return The column associated to the column's name.
     */
    Optional<DataFrameColumn> select(String column);

    /**
     * Shuffle the dataframe "iterations" times.
     * @param iterations The number of iterations.
     * @return The shuffled dataframe.
     */
    DataFrame shuffle(int iterations);

    /**
     * Get a row by an index.
     * @param index The index of the row.
     * @return The row associated to the index.
     * @throws IndexOutOfBoundsException If the index is greater than the rows size of the dataframe
     * or if the index is negative.
     */
    DataFrameRow rowAt(int index);

    /**
     * Select only the rows for the given column, the rows have value less than the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain numeric values.
     */
    DataFrame lessThan(String column, Number value);

    /**
     * Select only the rows for the given column, the rows have value less than the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain string values.
     */
    DataFrame lessThan(String column, String value);

    /**
     * Select only the rows for the given column, the rows have value less or equals than the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain numeric values.
     */
    DataFrame lessOrEqualsThan(String column, Number value);

    /**
     * Select only the rows for the given column, the rows have value less or equals than the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain string values.
     */
    DataFrame lessOrEqualsThan(String column, String value);

    /**
     * Select only the rows for the given column, the rows have value greater than the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain numeric values.
     */
    DataFrame greaterThan(String column, Number value);

    /**
     * Select only the rows for the given column, the rows have value greater than the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain string values.
     */
    DataFrame greaterThan(String column, String value);

    /**
     * Select only the rows for the given column, the rows have value greater or equals than the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain numeric values.
     */
    DataFrame greaterOrEqualsThan(String column, Number value);

    /**
     * Select only the rows for the given column, the rows have value greater or equals than the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain string values.
     */
    DataFrame greaterOrEqualsThan(String column, String value);

    /**
     * Select only the string rows that begin with the given prefix.
     * @param column The column's name.
     * @param prefix The prefix which the words must begin.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain string values.
     */
    DataFrame startsWith(String column, String prefix);

    /**
     * Select only the string rows that ends with the given suffix.
     * @param column The column's name.
     * @param prefix The suffix which the words must end.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain string values.
     */
    DataFrame endsWith(String column, String prefix);

    /**
     * Select only the rows for the given column that have values equals to the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain numeric values.
     */
    DataFrame equals(String column, Number value);

    /**
     * Select only the rows for the given column that have values equals to the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain string values.
     */
    DataFrame equals(String column, String value);

    /**
     * Select only the rows for the given column that have values not equals to the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain numeric values.
     */
    DataFrame notEquals(String column, Number value);

    /**
     * Select only the rows for the given column that have values not equals to the given value.
     * @param column The column's name.
     * @param value The value for the filter.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist or if the column
     * doesn't contain string values.
     */
    DataFrame notEquals(String column, String value);

    /**
     * Compute the dataframe by removing the duplicates for the given column.
     * @param column The column's name.
     * @return The filtered dataframe.
     * @throws IllegalOperationException If the column doesn't exist.
     */
    DataFrame distinct(String column);
}
