package com.terbah.sketch.api.data.dataframe;

import com.terbah.sketch.api.data.util.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import static com.terbah.sketch.api.data.dataframe.DefaultDataFrameColumn.DataFrameType;

/**
 * @author Dorian TERBAH
 *
 * This is the default implementation of the DataFrame interface.
 *
 * @since 1.0
 * @see DataFrame
 */
class DefaultDataFrame implements DataFrame
{
    /**
     * Default value when there are some string missing values.
     */
    protected static final String DEFAULT_STRING_VALUE = "null";

    /**
     * JSON key used to store the dataframe in a JSON object.
     */
    protected static final String DATA_JSON_KEY = "data";

    /**
     * Default value when there are some numeric missing values.
     */
    protected static final Number DEFAULT_NUMBER_VALUE = 0.0;

    /**
     * Map that associate a column name with its corresponding values.
     */
    private final Map<String, DefaultDataFrameColumn> columns;

    /**
     * Indicate the number of rows inside the dataframe.
     */
    private int rowsCount;

    /**
     * Indicate the number of columns inside the dataframe.
     */
    private int columnsCount;


    /**
     * Default constructor of the dataframe.
     */
    DefaultDataFrame()
    {
        this.columns = new HashMap<>();
        this.rowsCount = 0;
        this.columnsCount = 0;
    }

    static DataFrame fromCSV(File csvFile, boolean hasHeaders, String separator)
    {
        if (!csvFile.exists())
        {
            return null;
        }

        DefaultDataFrame dataframe = new DefaultDataFrame();
        List<String> lines;
        try
        {
            lines = Files.readAllLines(Paths.get(csvFile.getAbsolutePath()));
        }
        catch (IOException exception)
        {
            return dataframe;
        }

        if (lines.isEmpty())
        {
            return dataframe;
        }

        String headerLine = lines.get(0);
        List<String> headers = DefaultDataFrame.getHeaders(headerLine, hasHeaders, separator);
        dataframe.columnsCount = headers.size();

        if (hasHeaders)
        {
            lines.remove(0);
        }

        // used to avoid complexity during the processing of data.
        List<DefaultDataFrameColumn> columns = new ArrayList<>();
        dataframe.rowsCount = lines.size();

        for (int lineIndex = 0; lineIndex < lines.size(); ++lineIndex)
        {
            String currentLine = lines.get(lineIndex);
            String[] tokens = currentLine.split(separator);

            if (tokens.length != headers.size())
            {
                throw new MalformedCSVException("The file " + csvFile + " is not a valid csv file. There is some missing values");
            }

            for (int tokenIndex = 0; tokenIndex < tokens.length; ++tokenIndex)
            {
                String token = tokens[tokenIndex];
                // headers
                if (lineIndex == 0)
                {
                    DefaultDataFrameColumn.DataFrameType type = Utils.isNumber(token)
                            ? DefaultDataFrameColumn.DataFrameType.NUMERIC
                            : DefaultDataFrameColumn.DataFrameType.STRING;

                    String columnName = headers.get(tokenIndex);
                    DefaultDataFrameColumn column = new DefaultDataFrameColumn(type, columnName);
                    if (type.equals(DefaultDataFrameColumn.DataFrameType.NUMERIC))
                    {
                        double value = new BigDecimal(token).doubleValue();
                        column.add(value);
                    }
                    else
                    {
                        column.add(token);
                    }

                    dataframe.columns.put(columnName, column);
                    columns.add(column);
                }
                else
                {
                    // populate the data
                    DefaultDataFrameColumn currentColumn = columns.get(tokenIndex);
                    if (Utils.isNumber(token))
                    {
                        currentColumn.add(new BigDecimal(token).doubleValue());
                    }
                    else
                    {
                        currentColumn.add(token);
                    }
                }
            }
        }

        return dataframe;
    }

    /**
     * Compute the headers of a csv file.
     * @param headerLine The line with the headers.
     * @param hasHeaders Boolean that specify if the csv file has headers.
     * @param separator The separator used to separate the data.
     * @return A list with the computed headers.
     */
    private static List<String> getHeaders(String headerLine, boolean hasHeaders, String separator)
    {
        List<String> headers = new ArrayList<>();
        String[] tokens = headerLine.split(separator);

        if (hasHeaders)
        {
            headers = Arrays.asList(tokens);
        }
        else
        {
            for (int headerIndex = 0; headerIndex < tokens.length; ++headerIndex)
            {
                headers.add("header" + headerIndex);
            }
        }

        return headers;
    }

    @Override
    public int getColumnsCount()
    {
        return this.columnsCount;
    }

    @Override
    public int getRowsCount()
    {
        return this.rowsCount;
    }

    @Override
    public boolean exists(String column)
    {
        return this.columns.containsKey(column);
    }

    @Override
    public List<String> getHeaders()
    {
        return new ArrayList<>(this.columns.keySet());
    }

    @Override
    public DataFrame select(List<String> headers)
    {
        DefaultDataFrame dataframe = this.copy();
        for (String column : dataframe.getHeaders())
        {
            if (!headers.contains(column))
            {
                dataframe.remove(column);
                dataframe.columnsCount--;
            }
        }

        return dataframe;
    }

    @Override
    public boolean isEmpty()
    {
        return this.columns.isEmpty();
    }

    @Override
    public DataFrame abs()
    {
        DataFrame dataframe = this.copy();
        for (String column : this.columns.keySet())
        {
            Optional<DataFrameColumn> optionalColumn = dataframe.select(column);
            if (optionalColumn.isPresent())
            {
                DataFrameColumn dataColumn = optionalColumn.get();
                if (dataColumn.isNumericColumn())
                {
                    dataColumn.abs();
                }
            }
        }

        return dataframe;
    }

    @Override
    public DataFrame abs(String column)
    {
        Optional<DataFrameColumn> optionalColumn = this.select(column);
        if (optionalColumn.isEmpty())
        {
            throw new IllegalOperationException(String.format("the column %s doesn't exist.", column));
        }

        DataFrame dataframe = this.copy();
        if (dataframe.select(column).isPresent())
        {
            dataframe.select(column).get().abs();
        }
        return dataframe;
    }

    @Override
    public Number avg(String column)
    {
        Optional<DataFrameColumn> optionalColumn = this.select(column);
        if (optionalColumn.isEmpty())
        {
            throw new IllegalOperationException(String.format("the column %s doesn't exist.", column));
        }

        return optionalColumn.get().avg();
    }

    @Override
    public Number sum(String column)
    {
        Optional<DataFrameColumn> optionalColumn = this.select(column);
        if (optionalColumn.isEmpty())
        {
            throw new IllegalOperationException(String.format("the column %s doesn't exist.", column));
        }

        return optionalColumn.get().sum();
    }

    @Override
    public Number max(String column)
    {
        Optional<DataFrameColumn> optionalColumn = this.select(column);
        if (optionalColumn.isEmpty())
        {
            throw new IllegalOperationException(String.format("the column %s doesn't exist.", column));
        }

        return optionalColumn.get().max();
    }

    @Override
    public Number min(String column)
    {
        Optional<DataFrameColumn> optionalColumn = this.select(column);
        if (optionalColumn.isEmpty())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist.", column));
        }

        return optionalColumn.get().min();
    }

    @Override
    public DataFrame normalize(String column)
    {
        Optional<DataFrameColumn> optionalColumn = this.select(column);
        if (optionalColumn.isEmpty())
        {
            throw new IllegalOperationException(String.format("the column %s doesn't exist.", column));
        }

        DataFrame dataframe = this.copy();
        if (dataframe.select(column).isPresent())
        {
            dataframe.select(column).get().normalize();
        }
        return dataframe;
    }

    @Override
    public DataFrame normalize()
    {
        DataFrame dataframe = this.copy();
        for (String column : this.columns.keySet())
        {
            Optional<DataFrameColumn> optionalColumn = dataframe.select(column);
            if (optionalColumn.isPresent())
            {
                DataFrameColumn dataColumn = optionalColumn.get();
                if (dataColumn.isNumericColumn())
                {
                    dataColumn.normalize();
                }
            }
        }

        return dataframe;
    }

    @Override
    public DataFrame filter(String column, Predicate<DataFrameCell> predicate)
    {
        DefaultDataFrame dataframe = this.copy();
        DataFrameColumn columnToFilter = dataframe.columns.get(column);

        for (int i = 0; i < dataframe.rowsCount; )
        {
            DataFrameCell cell = columnToFilter.at(i);
            if (!predicate.test(cell))
            {
                for (String header : dataframe.getHeaders())
                {
                    DataFrameColumn dataColumn = dataframe.columns.get(header);
                    dataColumn.removeAt(i);
                }

                dataframe.rowsCount--;
            }
            else
            {
                i++;
            }
        }

        return dataframe;
    }

    @Override
    public DataFrame addStringColumn(String column, List<String> values)
    {
        DefaultDataFrame dataframe = this.copy();
        DefaultDataFrameColumn dataColumn = new DefaultDataFrameColumn(DataFrameType.STRING, column);
        values.forEach(dataColumn::add);

        int valuesSize = values.size();
        if (valuesSize < dataframe.rowsCount)
        {
            for (int i = 0; i < dataframe.rowsCount - valuesSize; ++i)
            {
                dataColumn.add(DEFAULT_STRING_VALUE);
            }
        }
        else if (valuesSize > dataframe.rowsCount)
        {
            for (var entry : dataframe.columns.entrySet())
            {
                DefaultDataFrameColumn col = entry.getValue();
                if (col.isStringColumn())
                {
                    for (int i = 0; i < valuesSize - col.count(); ++i)
                    {
                        col.add(DEFAULT_STRING_VALUE);
                    }
                }
                else if (col.isNumericColumn())
                {
                    for (int i = 0; i < valuesSize - col.count(); ++i)
                    {
                        col.add(DEFAULT_NUMBER_VALUE);
                    }
                }
            }

            dataframe.rowsCount = valuesSize;
        }
        dataframe.columns.put(column, dataColumn);
        dataframe.columnsCount = dataframe.columns.size();
        return dataframe;
    }

    @Override
    public DataFrame addNumericColumn(String column, List<Number> values)
    {
        DefaultDataFrame dataframe = this.copy();
        DefaultDataFrameColumn dataColumn = new DefaultDataFrameColumn(DataFrameType.NUMERIC, column);
        values.forEach(dataColumn::add);
        int valuesSize = values.size();

        if (valuesSize < dataframe.rowsCount)
        {
            for (int i = 0; i < dataframe.rowsCount - values.size(); ++i)
            {
                dataColumn.add(DEFAULT_STRING_VALUE);
            }
        }
        else if (valuesSize > dataframe.rowsCount)
        {
            for (var entry : dataframe.columns.entrySet()) {
                DefaultDataFrameColumn col = entry.getValue();
                if (col.isStringColumn()) {
                    for (int i = 0; i < valuesSize - col.count(); ++i) {
                        col.add(DEFAULT_STRING_VALUE);
                    }
                } else if (col.isNumericColumn()) {
                    for (int i = 0; i < valuesSize - col.count(); ++i) {
                        col.add(DEFAULT_NUMBER_VALUE);
                    }
                }
            }

            dataframe.rowsCount = valuesSize;
        }
        dataframe.columns.put(column, dataColumn);
        dataframe.columnsCount = dataframe.columns.size();
        return dataframe;
    }

    @Override
    public Optional<DataFrameColumn> remove(String column)
    {
        return Optional.ofNullable(this.columns.remove(column));
    }

    @Override
    public DefaultDataFrame copy()
    {
        DefaultDataFrame dataframe = new DefaultDataFrame();
        this.columns.forEach((key, column) -> dataframe.columns.put(key, (DefaultDataFrameColumn) column.copy()));
        dataframe.rowsCount = this.rowsCount;
        dataframe.columnsCount = this.columnsCount;
        return dataframe;
    }

    @Override
    public Optional<DataFrameColumn> select(String column)
    {
        return Optional.ofNullable(this.columns.get(column));
    }
    @Override
    public DataFrame shuffle(int iterations)
    {
        DataFrame dataframe = this.copy();
        Random random = new Random();

        for (int iteration = 0; iteration < iterations; ++iteration)
        {
            int sourceIndex = random.nextInt(this.getRowsCount());
            int targetIndex = random.nextInt(this.getRowsCount());

            for (String header : dataframe.getHeaders())
            {
                Optional<DataFrameColumn> columnOptional = dataframe.select(header);
                if (columnOptional.isPresent())
                {
                    DataFrameColumn column = columnOptional.get();
                    column.swap(sourceIndex, targetIndex);
                }
            }
        }

        return dataframe;
    }

    @Override
    public DataFrame lessThan(String column, Number value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isNumber()) && (cell.asNumber().doubleValue() < value.doubleValue());

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame lessThan(String column, String value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isStringColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain string values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isString()) && cell.asString().compareTo(value) < 0;

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame lessOrEqualsThan(String column, Number value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isNumber()) && (cell.asNumber().doubleValue() <= value.doubleValue());

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame lessOrEqualsThan(String column, String value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isStringColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain string values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isString()) && ((cell.asString().compareTo(value) < 0) || cell.asString().equals(value));

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame greaterThan(String column, Number value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isNumber()) && (cell.asNumber().doubleValue() > value.doubleValue());

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame greaterThan(String column, String value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isStringColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain string values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isString()) && cell.asString().compareTo(value) > 0;

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame greaterOrEqualsThan(String column, Number value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isNumber()) && (cell.asNumber().doubleValue() >= value.doubleValue());

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame greaterOrEqualsThan(String column, String value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isStringColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain string values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isString()) && ((cell.asString().compareTo(value) > 0) || cell.asString().equals(value));

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame startsWith(String column, String prefix)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isStringColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain string values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isString()) && cell.asString().startsWith(prefix);

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame endsWith(String column, String suffix)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isStringColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain string values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isString()) && cell.asString().endsWith(suffix);

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame equals(String column, Number value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isNumber()) && cell.asNumber().doubleValue() == value.doubleValue();

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame equals(String column, String value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isStringColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain string values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isString()) && cell.asString().equals(value);

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame notEquals(String column, Number value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isNumericColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain numeric values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isNumber()) && cell.asNumber().doubleValue() != value.doubleValue();

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame notEquals(String column, String value)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DataFrameColumn dataColumn = this.select(column).get();
        if (!dataColumn.isStringColumn())
        {
            throw new IllegalOperationException(String.format("The column %s doesn't contain string values.", column));
        }

        Predicate<DataFrameCell> predicate = cell -> (cell.isString()) && !cell.asString().equals(value);

        return this.filter(column, predicate);
    }

    @Override
    public DataFrame distinct(String column)
    {
        if (!this.exists(column))
        {
            throw new IllegalOperationException(String.format("The column %s doesn't exist", column));
        }

        DefaultDataFrame dataframe = this.copy();
        DefaultDataFrameColumn dataColumn = dataframe.columns.get(column);

        for (int i = 0; i < dataframe.rowsCount; )
        {
            DataFrameCell cell = dataColumn.at(i);
            Object value = cell.value();

            if (dataframe.isDuplicate(column, value))
            {
                // remove the duplicate
                for (int j = 0; j < dataframe.rowsCount; ++j)
                {
                    if (j != i)
                    {
                        DataFrameCell otherCell = dataColumn.at(j);
                        Object otherValue = otherCell.value();
                        if (value.equals(otherValue))
                        {
                            // remove value at the index j
                            for (String header : dataframe.getHeaders())
                            {
                                DataFrameColumn col = dataframe.columns.get(header);
                                col.removeAt(j);
                            }

                            dataframe.rowsCount--;
                        }
                    }
                }
            }
            else
            {
                i++;
            }
        }

        return dataframe;
    }

    /**
     * Check if a value is duplicate in the dataframe for a given column.
     * @param column The column's name.
     * @param value The value to check.
     * @return <code>true</code> If the value is duplicate, else <code>false</code>.
     */
    private boolean isDuplicate(String column, Object value)
    {
        DataFrameColumn dataColumn = this.columns.get(column);
        int count = 0;

        for (int index = 0; index < dataColumn.count(); ++index)
        {
            DataFrameCell cell = dataColumn.at(index);
            if (cell.value().equals(value))
            {
                count++;
            }
        }

        return count > 1;
    }

    @Override
    public DefaultDataFrameRow rowAt(int index)
    {
        if ((index > this.rowsCount) || (index < 0))
        {
            throw new IndexOutOfBoundsException(index);
        }

        DefaultDataFrameRow row = new DefaultDataFrameRow();
        this.columns.forEach((header, col) -> row.put(header, col.at(index)));

        return row;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        if (this.columns.isEmpty()) return "";

        for (String name : this.getHeaders())
        {
            builder.append(name).append("   ");
        }

        builder.append("\n");

        for (int i = 0; i < this.rowsCount; ++i)
        {
            for (String name : this.getHeaders())
            {
                DataFrameColumn column = this.columns.get(name);
                builder.append(column.at(i).value()).append("   ");
            }

            builder.append("\n");
        }

        return builder.toString();
    }
}
