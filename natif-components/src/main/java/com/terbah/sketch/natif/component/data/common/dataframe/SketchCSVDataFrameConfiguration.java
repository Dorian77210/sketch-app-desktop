package com.terbah.sketch.natif.component.data.common.dataframe;

import java.io.File;

/**
 * @author Dorian TERBAH
 *
 * This class represents the data associated to a csv file chooser.
 *
 * @version 1.0
 */

public class SketchCSVDataFrameConfiguration
{
    /**
     * The CSV file.
     */
    private File csvFile;

    /**
     * Boolean used to know if the CSV file has headers.
     */
    private Boolean hasHeaders;

    /**
     * Separator used in the csv file.
     */
    private String separator;

    /**
     * The default CSV separator for the CVS files.
     */
    private static final String DEFAULT_CSV_SEPARATOR = ";";

    public SketchCSVDataFrameConfiguration()
    {
        this.csvFile = null;
        this.hasHeaders = null;
        this.separator = DEFAULT_CSV_SEPARATOR;
    }

    public boolean isValidCSVFile()
    {
        if (this.csvFile == null) return false;
        return this.csvFile.getAbsolutePath().endsWith(".csv");
    }

    public File getCsvFile() {
        return csvFile;
    }

    public Boolean getHasHeaders() {
        return hasHeaders;
    }

    public String getSeparator() {
        return separator;
    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }

    public void setHasHeaders(Boolean hasHeaders) {
        this.hasHeaders = hasHeaders;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
