package com.terbah.sketch.natif.component.ui.common.dataframe;

import com.terbah.sketch.api.data.dataframe.DataFrame;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorian TERBAH
 *
 * Class that represents a spreadsheet to visualize a data frame.
 *
 * @version 1.0
 */
public class SketchDataFrameView extends GridPane
{
    /**
     * The  data frame used in this sheet.
     */
    private final DataFrame data;

    /**
     * Title positioned on the top.
     */
    private final String title;

    /**
     * Create a new SketchDataFrameView
     * @param data The associated data frame.
     * @param title The title of the view
     */
    public SketchDataFrameView(DataFrame data, final String title)
    {
        this.data = data;
        this.title = title;
        this.setup();
    }

    /**
     * Set up the spreadsheet.
     */
    private void setup()
    {
        // Headers
        if (this.data != null)
        {
            List<String> headers = this.data.getHeaders();
            for (int headerIndex = 0; headerIndex < headers.size(); ++headerIndex)
            {
                String header = headers.get(headerIndex);
                Label headerLabel = new Label(header);
                this.add(headerLabel, headerIndex, 1);
            }

            for (int headerIndex = 0; headerIndex < headers.size(); ++headerIndex)
            {
                List<String> values = new ArrayList<>();
                String header = headers.get(headerIndex);
                values.addAll(this.data.select(header)
                        .get()
                        .values()
                        .stream()
                        .map(Object::toString)
                        .toList()
                );

                ListView<String> dataView = new ListView<>(FXCollections.observableList(FXCollections.observableList(values)));
                this.add(dataView, headerIndex, 2);
            }
        }
        else
        {
            this.add(new ListView<String>(), 0, 1);
        }
    }
}
