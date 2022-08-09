package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;

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
