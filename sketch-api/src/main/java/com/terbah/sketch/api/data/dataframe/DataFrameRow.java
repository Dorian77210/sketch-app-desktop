package com.terbah.sketch.api.data.dataframe;

import java.util.List;
import java.util.Optional;

/**
 * @author Dorian TERBAH
 *
 * This interface defines method to interact with a row in a dataframe.
 *
 * @since 1.0
 */
public interface DataFrameRow
{
    /**
     * Get a row by a column name.
     * @param column The column name.
     * @return An optional with the corresponding cell associated to the column's
     * name. It could be empty if the column's name doesn't exist.
     */
    Optional<DataFrameCell> at(String column);

    /**
     * @return The list of column's names.
     */
    List<String> getHeaders();
}
