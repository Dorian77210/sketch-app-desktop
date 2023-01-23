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
 * This is the popup associated to the component SketchNumericDataframeNormalizeComponent.
 * It will display the dataframe that will be normalized.
 *
 * @version 1.0
 */
public class SketchDataFrameNormalizerPopup extends SketchConfigurationPopup
{
    /**
     * Create a new popup with the owner window.
     *
     * @param wrapper The data wrapper used by this popup
     */
    public SketchDataFrameNormalizerPopup(final SketchDataWrapper<DataFrame> wrapper) {
        super("Numeric dataframe Normalizer");
        BorderPane pane = new BorderPane();
        pane.setCenter(new SketchDataFrameView(wrapper.getData(), "Data that will be proceeded"));
        this.setScene(new Scene(pane));
    }
}
