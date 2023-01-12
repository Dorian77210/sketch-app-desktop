package com.terbah.sketch.api.ui;

import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Dorian TERBAH
 *
 *  This class represents the base popup for the components configuration.
 *
 * @version 1.0
 */
public class SketchConfigurationPopup extends Stage {

    /**
     * Create a new popup with the owner window and a specific title.
     */
    public SketchConfigurationPopup(String title)
    {
        super();
        this.initModality(Modality.WINDOW_MODAL);
    }
}
