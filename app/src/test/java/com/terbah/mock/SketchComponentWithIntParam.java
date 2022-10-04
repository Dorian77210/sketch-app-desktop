package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;

public class SketchComponentWithIntParam implements SketchComponent<Integer> {

    @Override
    public Integer execute() {
        return 10;
    }

    @Override
    public SketchComponent<Integer> copy() {
        return new SketchComponentWithIntParam();
    }
}
