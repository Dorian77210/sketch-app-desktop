package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;

public class SketchInvalidComponent implements SketchComponent<String> {

    @Override
    public String execute() {
        return "";
    }

    @Override
    public SketchComponent<String> copy() {
        return null;
    }
}
