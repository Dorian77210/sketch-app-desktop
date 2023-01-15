package com.terbah.sketch.api.data.dataframe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.terbah.sketch.api.data.dataframe.DataFrameColumn.DataFrameType;

/**
 * @author Dorian TERBAH
 *
 * This factory will create a dataframe from a csv file.
 *
 * @since 1.0
 */
public class DataFrameFactory
{
    /**
     * Create a dataframe associated to a csv file.
     * @param csvFile The csv file.
     * @param hasHeaders Boolean to precise if the CSV file has headers or not.
     * @param separator The separator used to separate the data inside the CSV file.
     * @return The dataframe associated to the CSV file if the file is correctly formed, else <code>null</code>.
     * @throws MalformedCSVException If the csv file is malformed.
     */
    public static DataFrame fromCSV(File csvFile, boolean hasHeaders, String separator)
    {
        return DefaultDataFrame.fromCSV(csvFile, hasHeaders, separator);
    }

    /**
     * Create an empty dataframe.
     * @param config A map with the headers associated to the type of column.
     * @return The dataframe created.
     */
    public static DataFrame emptyDataframe(Map<String, DataFrameType> config)
    {
        DataFrame dataframe = new DefaultDataFrame();
        for (var entry : config.entrySet())
        {
            String header = entry.getKey();
            DataFrameType type = entry.getValue();

            if (type.equals(DataFrameType.STRING))
            {
                dataframe = dataframe.addStringColumn(header, new ArrayList<>());
            }
            else if (type.equals(DataFrameType.NUMERIC))
            {
                dataframe = dataframe.addNumericColumn(header, new ArrayList<>());
            }
        }

        return dataframe;
    }

    /**
     * @return An empty dataframe.
     */
    public static DataFrame emptyDataframe()
    {
        return new DefaultDataFrame();
    }
}
