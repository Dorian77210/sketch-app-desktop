package com.terbah.sketch.api.data.dataframe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorian TERBAH
 *
 * This is the default implementation of the interface DataFrameColumn.
 *
 * @since 1.0
 * @see DataFrameColumn
 */
class DefaultDataFrameColumn implements DataFrameColumn
{
    /**
     * List of cells contained by this column.
     */
    private List<DataFrameCell> cells;

    /**
     * The type of data.
     */
    private final DataFrameType type;

    /**
     * The name of the column.
     */
    private final String columnName;

    /**
     * Default constructor of the DefaultDataFrameColumn.
     * @param type The type of data.
     * @param columnName The name of the column.
     */
    DefaultDataFrameColumn(DataFrameType type, String columnName)
    {
        this.cells = new ArrayList<>();
        this.type = type;
        this.columnName = columnName;
    }

    @Override
    public int size()
    {
        return this.cells.size();
    }

    @Override
    public DataFrameCell at(int index)
    {
        if ((index < 0) || (index > this.cells.size()))
        {
            throw new IndexOutOfBoundsException(index);
        }

        return this.cells.get(index);
    }

    @Override
    public void removeAt(int index)
    {
        if ((index < 0) || (index > this.cells.size()))
        {
            throw new IndexOutOfBoundsException(index);
        }

        this.cells.remove(index);
    }

    @Override
    public void removeAll(Object item)
    {
        this.cells = this.cells.stream()
                .filter(cell -> !cell.equals(item))
                .toList();
    }

    @Override
    public void add(String value)
    {
        if (this.isNumericColumn())
        {
            throw new IllegalOperationException("The column cannot contain Numeric values");
        }

        this.cells.add(new DefaultDataFrameCell(value));
    }

    @Override
    public void add(Number value)
    {
        if (this.isStringColumn())
        {
            throw new IllegalOperationException("The column cannot contain String values");
        }

        this.cells.add(new DefaultDataFrameCell(value));
    }

    @Override
    public String getName()
    {
        return this.columnName;
    }

    @Override
    public void swap(int sourceIndex, int targetIndex)
    {
        if ((sourceIndex < 0) || (sourceIndex > this.cells.size()))
        {
            throw new IndexOutOfBoundsException(sourceIndex);
        }

        if ((targetIndex < 0 ) || (targetIndex > this.cells.size()))
        {
            throw new IndexOutOfBoundsException(targetIndex);
        }

        DataFrameCell tempCell = this.cells.get(sourceIndex);
        this.cells.set(sourceIndex, this.cells.get(targetIndex));
        this.cells.set(targetIndex, tempCell);
    }

    @Override
    public boolean isNumericColumn()
    {
        return this.type.equals(DataFrameType.NUMERIC);
    }

    @Override
    public boolean isStringColumn()
    {
        return this.type.equals(DataFrameType.STRING);
    }

    @Override
    public Number avg()
    {
        if (!this.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values. The average cannot be computed.", this.columnName));
        }

        return this.cells.stream()
                .mapToDouble(cell -> cell.asNumber().doubleValue())
                .average()
                .orElse(0.0);
    }

    @Override
    public Number sum()
    {
        if (!this.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values. The sum cannot be computed.", this.columnName));
        }

        return this.cells.stream()
                .mapToDouble(cell -> cell.asNumber().doubleValue())
                .sum();
    }

    @Override
    public Number max()
    {
        if (!this.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values. The maximum cannot be computed.", this.columnName));
        }

        return this.cells.stream()
                .mapToDouble(cell -> cell.asNumber().doubleValue())
                .max()
                .orElse(0.0);
    }

    @Override
    public Number min()
    {
        if (!this.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values. The minimum cannot be computed", this.columnName));
        }

        return this.cells.stream()
                .mapToDouble(cell -> cell.asNumber().doubleValue())
                .min()
                .orElse(0.0);
    }

    @Override
    public void normalize()
    {
        if (!this.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values. The normalization cannot be computed.", this.columnName));
        }

        double min = this.min().doubleValue();
        double max = this.max().doubleValue();

        for (int index = 0; index < this.cells.size(); ++index)
        {
            double value = this.cells.get(index).asNumber().doubleValue();
            DataFrameCell cell = new DefaultDataFrameCell((value - min) / (max - min));
            this.cells.set(index, cell);
        }
    }

    @Override
    public void abs()
    {
        if (!this.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values. The absolute function cannot be computed.", this.columnName));
        }

        for (int index = 0; index < this.cells.size(); ++index)
        {
            double value = this.cells.get(index).asNumber().doubleValue();
            DataFrameCell cell = new DefaultDataFrameCell(Math.abs(value));
            this.cells.set(index, cell);
        }
    }

    @Override
    public DataFrameColumn copy()
    {
        DefaultDataFrameColumn column = new DefaultDataFrameColumn(this.type, this.columnName);

        this.cells.forEach(cell -> {
            if (this.isStringColumn())
            {
                column.cells.add(new DefaultDataFrameCell(cell.asString()));
            }
            else if (this.isNumericColumn())
            {
                column.cells.add(new DefaultDataFrameCell(cell.asNumber()));
            }
        });

        return column;
    }

    @Override
    public int count()
    {
        return this.cells.size();
    }

    @Override
    public List<Object> values()
    {
        return this.cells.stream()
                .map(DataFrameCell::value)
                .toList();
    }
}
