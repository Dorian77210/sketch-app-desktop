package com.terbah.sketch.natif.component.ui.common.dataframe;

import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

/**
 * @author Dorian TERBAH
 *
 * Popup that has to show a data frame.
 *
 * @version 1.0
 */
public class SketchDataFrameViewPopup extends SketchConfigurationPopup
{
    /**
     * Wrapper of the used dataframe in the popup.
     */
    private SketchDataWrapper<DataFrame> data;

    /**
     * Create a new popup with the owner window and a specific title.
     *
     * @param data The data associated to this popup.
     */
    public SketchDataFrameViewPopup(SketchDataWrapper<DataFrame> data)
    {
        super("Numeric Data Frame Viewer");
        this.data = data;

        BorderPane pane = new BorderPane();

        if (data.isDataAvailable())
        {
            pane.setCenter(new SketchDataFrameView(data.getData(), "Data"));
        }

        this.setScene(new Scene(pane));
    }
}
