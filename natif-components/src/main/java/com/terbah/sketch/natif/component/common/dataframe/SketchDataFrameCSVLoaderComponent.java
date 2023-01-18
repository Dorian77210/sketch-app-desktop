package com.terbah.sketch.natif.component.common.dataframe;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.dataframe.DataFrameFactory;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.data.common.dataframe.SketchCSVDataFrameConfiguration;
import com.terbah.sketch.natif.component.ui.common.dataframe.SketchDataFrameCSVLoaderPopup;

import java.io.File;

/**
 * @author Dorian TERBAH
 *
 * This component can load a numeric dataframe according to a file.
 *
 * @version 1.0
 */

@ComponentConfiguration(
        namespace = "Common/DataFrame",
        name = "CSV DataFrame loader"
)
public class SketchDataFrameCSVLoaderComponent implements SketchComponent<DataFrame>
{
    /**
     * The data associated
     */
    private final SketchCSVDataFrameConfiguration data;

    /**
     * JSON key used to store the path of the CSV file.
     */
    protected static final String FILE_JSON_KEY = "file";

    /**
     * JSON key used to store the separator used in the CSV file.
     */
    protected static final String SEPARATOR_JSON_KEY = "separator";

    /**
     * JSON key used to know if the csv has headers or not.
     */
    protected static final String HEADERS_JSON_KEY = "headers";

    /**
     * Create a new SketchNumericDataFrameCSVLoaderComponent.
     */
    public SketchDataFrameCSVLoaderComponent()
    {
        this.data = new SketchCSVDataFrameConfiguration();
    }

    @Override
    public DataFrame execute() throws SketchComponentExecuteException
    {
        File file = this.data.getCsvFile();
        if (file == null)
        {
            throw new SketchComponentExecuteException("The file is null.");
        }
        else if (!file.exists())
        {
            throw new SketchComponentExecuteException("The file " + file + " doesn't exist !");
        }
        else if (!this.data.isValidCSVFile())
        {
            throw new SketchComponentExecuteException("The file " + file + " has not the well CSV extension.");
        }
        else if (this.data.getSeparator().isEmpty())
        {
            throw new SketchComponentExecuteException("The CSV separator must not be empty.");
        }

        DataFrame data = DataFrameFactory.fromCSV(file, this.data.getHasHeaders(), this.data.getSeparator());

        if (data == null)
        {
            throw new SketchComponentExecuteException("The reading of the file " + file + " encountered an error.");
        }

        return data;
    }

    @Override
    public SketchComponent<DataFrame> copy() {
        SketchDataFrameCSVLoaderComponent component = new SketchDataFrameCSVLoaderComponent();
        component.data.setCsvFile(this.data.getCsvFile());
        component.data.setSeparator(this.data.getSeparator());
        component.data.setHasHeaders(this.data.getHasHeaders());
        return component;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return new SketchDataFrameCSVLoaderPopup(this.data);
    }
}
