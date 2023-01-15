package com.terbah.sketch.api.data.dataframe;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.terbah.sketch.api.data.dataframe.DefaultDataFrame.DATA_JSON_KEY;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dorian TERBAH
 *
 * Test the class DataFrame.
 *
 * @since 1.0
 */
public class DataFrameTest
{
    @Test
    public void testFromValidCSV()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        // first column
        Optional<DataFrameColumn> optionalColumn = dataframe.select("x");
        assertTrue(optionalColumn.isPresent());
        assertTrue(optionalColumn.get().isNumericColumn());
        assertFalse(optionalColumn.get().isStringColumn());
        DataFrameColumn column = optionalColumn.get();
        assertNotNull(column);
        assertTrue(column.isNumericColumn());
        assertEquals(10.0, column.at(0).asNumber());
        assertEquals(20.0, column.at(1).asNumber());
        assertEquals(-10.0, column.at(2).asNumber());

        // second column
        optionalColumn = dataframe.select("y");
        assertTrue(optionalColumn.isPresent());
        assertTrue(optionalColumn.get().isNumericColumn());
        assertFalse(optionalColumn.get().isStringColumn());
        column = optionalColumn.get();
        assertNotNull(column);
        assertTrue(column.isNumericColumn());
        assertEquals(15.0, column.at(0).asNumber());
        assertEquals(369.0, column.at(1).asNumber());
        assertEquals(-9.0, column.at(2).asNumber());

        optionalColumn = dataframe.select("label");
        assertTrue(optionalColumn.isPresent());
        assertTrue(optionalColumn.get().isStringColumn());
        assertFalse(optionalColumn.get().isNumericColumn());
        column = optionalColumn.get();
        assertNotNull(column);
        assertEquals("France", column.at(0).asString());
        assertEquals("Espagne", column.at(1).asString());
        assertEquals("Italie", column.at(2).asString());
    }

    @Test
    public void testMinValue()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());
        assertEquals(-10.0, dataframe.min("x"));
        assertEquals(-9.0, dataframe.min("y"));

        try
        {
            dataframe.min("label");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.min("invalid");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testMaxValue()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());
        assertEquals(20.0, dataframe.max("x"));
        assertEquals(369.0, dataframe.max("y"));

        try
        {
            dataframe.max("label");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.max("invalid");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testSumValue()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());
        assertEquals(20.0, dataframe.sum("x"));
        assertEquals(375.0, dataframe.sum("y"));

        try
        {
            dataframe.sum("label");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.sum("invalid");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testAvgValue()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());
        assertEquals(20.0 / 3.0, dataframe.avg("x"));
        assertEquals(125.0, dataframe.avg("y"));

        try
        {
            dataframe.avg("label");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.avg("invalid");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testAbsValueOnSingleColumn()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.abs("x");
        Optional<DataFrameColumn> optionalColumn = dataframe.select("x");
        assertTrue(optionalColumn.isPresent());
        DataFrameColumn column = optionalColumn.get();
        assertEquals(3, column.count());
        assertEquals(10.0, column.at(0).asNumber());
        assertEquals(20.0, column.at(1).asNumber());
        assertEquals(10.0, column.at(2).asNumber());

        dataframe = dataframe.abs("y");
        optionalColumn = dataframe.select("y");
        assertTrue(optionalColumn.isPresent());
        column = optionalColumn.get();
        assertEquals(3, column.count());
        assertEquals(15.0, column.at(0).asNumber());
        assertEquals(369.0, column.at(1).asNumber());
        assertEquals(9.0, column.at(2).asNumber());

        try
        {
            dataframe = dataframe.abs("label");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe = dataframe.abs("invalid");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testAbsOnDataframe()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.abs();

        Optional<DataFrameColumn> optionalColumn = dataframe.select("x");
        assertTrue(optionalColumn.isPresent());
        DataFrameColumn column = optionalColumn.get();
        assertEquals(3, column.count());
        assertEquals(10.0, column.at(0).asNumber());
        assertEquals(20.0, column.at(1).asNumber());
        assertEquals(10.0, column.at(2).asNumber());

        optionalColumn = dataframe.select("y");
        assertTrue(optionalColumn.isPresent());
        column = optionalColumn.get();
        assertEquals(3, column.count());
        assertEquals(15.0, column.at(0).asNumber());
        assertEquals(369.0, column.at(1).asNumber());
        assertEquals(9.0, column.at(2).asNumber());
    }

    @Test
    public void testSelectMultipleColumns()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.select(List.of("x", "y"));
        assertEquals(2, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());
        assertTrue(dataframe.exists("x"));
        assertTrue(dataframe.exists("y"));
        assertFalse(dataframe.exists("label"));
    }

    @Test
    public void testNormalizeOnSingleColumn()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());
        dataframe = dataframe.normalize("x");
        Optional<DataFrameColumn> optionalColumn = dataframe.select("x");
        assertTrue(optionalColumn.isPresent());
        DataFrameColumn column = optionalColumn.get();
        assertEquals(3, column.count());
        assertEquals(2.0 / 3.0, column.at(0).asNumber());
        assertEquals(1.0, column.at(1).asNumber());
        assertEquals(0.0, column.at(2).asNumber());

        dataframe = dataframe.normalize("y");
        optionalColumn = dataframe.select("y");
        assertTrue(optionalColumn.isPresent());
        column = optionalColumn.get();
        assertEquals(3, column.count());
        assertEquals(0.06349206349206349, column.at(0).asNumber());
        assertEquals(1.0, column.at(1).asNumber());
        assertEquals(0.0, column.at(2).asNumber());

        try
        {
            dataframe = dataframe.normalize("label");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe = dataframe.normalize("invalid");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testAddStringColumnWithLessSize()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        List<String> values = List.of("toto", "titi");
        dataframe = dataframe.addStringColumn("column", values);
        assertNotNull(dataframe);
        assertEquals(4, dataframe.getColumnsCount());

        Optional<DataFrameColumn> optionalColumn = dataframe.select("column");
        assertTrue(optionalColumn.isPresent());
        DataFrameColumn column = optionalColumn.get();
        assertTrue(column.isStringColumn());
        assertEquals(3, column.count());
        assertEquals("toto", column.at(0).asString());
        assertEquals("titi", column.at(1).asString());
        assertEquals(DefaultDataFrame.DEFAULT_STRING_VALUE, column.at(2).asString());
    }

    @Test
    public void testAddStringColumnWithGreatSize()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        List<String> values = List.of("toto", "titi", "tutu", "tata");
        dataframe = dataframe.addStringColumn("column", values);
        assertNotNull(dataframe);
        assertEquals(4, dataframe.getColumnsCount());
        assertEquals(4, dataframe.getRowsCount());

        Optional<DataFrameColumn> optionalColumn = dataframe.select("column");
        assertTrue(optionalColumn.isPresent());
        DataFrameColumn column = optionalColumn.get();
        assertTrue(column.isStringColumn());
        assertEquals(4, column.count());
        assertEquals("toto", column.at(0).asString());
        assertEquals("titi", column.at(1).asString());
        assertEquals("tutu", column.at(2).asString());
        assertEquals("tata", column.at(3).asString());

        optionalColumn = dataframe.select("x");
        assertTrue(optionalColumn.isPresent());
        column = optionalColumn.get();
        assertTrue(column.isNumericColumn());
        assertEquals(4, column.count());
        assertEquals(DefaultDataFrame.DEFAULT_NUMBER_VALUE, column.at(3).asNumber());
    }

    @Test
    public void testRows()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        Map<String, List<Object>> map = Map.of(
                "x", List.of(10.0, 20.0, -10.0),
                "y", List.of(15.0, 369.0, -9.0),
                "label", List.of("France", "Espagne", "Italie")
        );

        for (var entry : map.entrySet())
        {
            String column = entry.getKey();
            List<Object> values = entry.getValue();

            for (int index = 0; index < dataframe.getRowsCount(); ++index)
            {
                DataFrameRow row = dataframe.rowAt(index);
                Optional<DataFrameCell> optionalCell = row.at(column);
                assertTrue(optionalCell.isPresent());
                DataFrameCell cell = optionalCell.get();
                assertEquals(values.get(index), cell.value());
            }
        }
    }

    @Test
    public void testLessThanNumber()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.lessThan("x", 10.0);
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(1, dataframe.getRowsCount());
        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(-10.0, row.at("x").get().asNumber());
        assertEquals(-9.0, row.at("y").get().asNumber());
        assertEquals("Italie", row.at("label").get().asString());

        try
        {
            dataframe.lessThan("invalid", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.lessThan("label", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testLessThanString()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.lessThan("label", "F");
        assertNotNull(dataframe);
        assertEquals(1, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(20.0, row.at("x").get().asNumber());
        assertEquals(369.0, row.at("y").get().asNumber());
        assertEquals("Espagne", row.at("label").get().asString());

        try
        {
            dataframe.lessThan("invalid", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.lessThan("x", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testGreaterThanNumber()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.greaterThan("x", 10.0);
        assertNotNull(dataframe);
        assertEquals(1, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(20.0, row.at("x").get().asNumber());
        assertEquals(369.0, row.at("y").get().asNumber());
        assertEquals("Espagne", row.at("label").get().asString());

        try
        {
            dataframe.greaterThan("invalid", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.greaterThan("label", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testGreaterThanString()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.greaterThan("label", "G");
        assertNotNull(dataframe);
        assertEquals(1, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(-10.0, row.at("x").get().asNumber());
        assertEquals(-9.0, row.at("y").get().asNumber());
        assertEquals("Italie", row.at("label").get().asString());

        try
        {
            dataframe.greaterThan("invalid", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.greaterThan("x", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testStartsWith()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.startsWith("label", "Esp");
        assertNotNull(dataframe);
        assertEquals(1, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(20.0, row.at("x").get().asNumber());
        assertEquals(369.0, row.at("y").get().asNumber());
        assertEquals("Espagne", row.at("label").get().asString());

        try
        {
            dataframe.startsWith("invalid", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.startsWith("x", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testEndsWith()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.endsWith("label", "agne");
        assertNotNull(dataframe);
        assertEquals(1, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(20.0, row.at("x").get().asNumber());
        assertEquals(369.0, row.at("y").get().asNumber());
        assertEquals("Espagne", row.at("label").get().asString());

        try
        {
            dataframe.startsWith("invalid", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.startsWith("x", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testEqualsNumericValue()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.equals("x", 20.0);
        assertNotNull(dataframe);
        assertEquals(1, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(20.0, row.at("x").get().asNumber());
        assertEquals(369.0, row.at("y").get().asNumber());
        assertEquals("Espagne", row.at("label").get().asString());

        try
        {
            dataframe.equals("invalid", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.equals("label", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testEqualsStringValue()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.equals("label", "Espagne");
        assertNotNull(dataframe);
        assertEquals(1, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(20.0, row.at("x").get().asNumber());
        assertEquals(369.0, row.at("y").get().asNumber());
        assertEquals("Espagne", row.at("label").get().asString());

        try
        {
            dataframe.equals("invalid", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.equals("x", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testNotEqualsNumeric()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.notEquals("x", 10.0).notEquals("x", -10.0);
        assertNotNull(dataframe);
        assertEquals(1, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(20.0, row.at("x").get().asNumber());
        assertEquals(369.0, row.at("y").get().asNumber());
        assertEquals("Espagne", row.at("label").get().asString());

        try
        {
            dataframe.equals("invalid", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.equals("label", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testNotEqualsString()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.notEquals("label", "France").notEquals("label", "Italie");
        assertNotNull(dataframe);
        assertEquals(1, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(20.0, row.at("x").get().asNumber());
        assertEquals(369.0, row.at("y").get().asNumber());
        assertEquals("Espagne", row.at("label").get().asString());

        try
        {
            dataframe.notEquals("invalid", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.notEquals("x", "value");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testDistinctOnColumn()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data-duplicate.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(5, dataframe.getRowsCount());

        dataframe = dataframe.distinct("x");
        assertNotNull(dataframe);
        assertEquals(3, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(2);
        assertEquals(-10.0, row.at("x").get().asNumber());
        assertEquals(6.0, row.at("y").get().asNumber());
        assertEquals("Italie", row.at("label").get().asString());
    }

    @Test
    public void testLessOrEqualsThanNumericValue()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/data.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(3, dataframe.getColumnsCount());
        assertEquals(3, dataframe.getRowsCount());

        dataframe = dataframe.lessOrEqualsThan("x", 10.0);
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertEquals(2, dataframe.getRowsCount());

        DataFrameRow row = dataframe.rowAt(0);
        assertEquals(10.0, row.at("x").get().asNumber());
        assertEquals(15.0, row.at("y").get().asNumber());
        assertEquals("France", row.at("label").get().asString());

        row = dataframe.rowAt(1);
        assertEquals(-10.0, row.at("x").get().asNumber());
        assertEquals(-9.0, row.at("y").get().asNumber());
        assertEquals("Italie", row.at("label").get().asString());

        try
        {
            dataframe.lessOrEqualsThan("invalid", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }

        try
        {
            dataframe.lessOrEqualsThan("label", 10.0);
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testBadCSVFile()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/bad-csv-file.csv")).getFile());

        try
        {
            DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ";");
            fail();
        }
        catch (IllegalOperationException exception)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testPopCSV()
    {
        File csvFile = new File(Objects.requireNonNull(DataFrameTest.class.getResource("/file/pop.csv")).getFile());
        DataFrame dataframe = DataFrameFactory.fromCSV(csvFile, true, ",");
        assertNotNull(dataframe);
        assertFalse(dataframe.isEmpty());
        assertTrue(dataframe.exists("Population"));
        Optional<DataFrameColumn> opt = dataframe.select("Population");
        assertTrue(opt.isPresent());
        DataFrameColumn column = opt.get();
        System.out.println(column.min());
    }
}
