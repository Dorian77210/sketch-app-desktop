package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;

public class SketchInvalidComponent implements SketchComponent<String> {

    @Override
    public String execute() {
        return "";
    }

    @Override
    public SketchComponent<String> copy() {
        return null;
    }

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return null;
    }
}
