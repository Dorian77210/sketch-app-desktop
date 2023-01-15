package com.terbah.sketch.api.data.dataframe;

import java.util.*;

public class DefaultDataFrameRow implements DataFrameRow
{
    /**
     * Associated cells to the row.
     */
    private final Map<String, DataFrameCell> cells;

    /**
     * Default constructor.
     */
    public DefaultDataFrameRow()
    {
        this.cells = new HashMap<>();
    }

    /**
     * Create a data row with a map.
     * @param cells The cells.
     */
    public DefaultDataFrameRow(Map<String, DataFrameCell> cells)
    {
        this.cells = cells;
    }

    @Override
    public Optional<DataFrameCell> at(String column)
    {
        return Optional.ofNullable(this.cells.get(column));
    }

    @Override
    public List<String> getHeaders()
    {
        return new ArrayList<>(this.cells.keySet());
    }

    /**
     * Create a new entry in the row.
     * @param column The column name.
     * @param cell The associated cell.
     */
    void put(String column, DataFrameCell cell)
    {
        this.cells.put(column, cell);
    }
}
