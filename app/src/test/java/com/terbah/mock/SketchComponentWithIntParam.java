package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentName;
import com.terbah.sketch.api.annotation.ComponentNamespace;

@ComponentName("SketchComponentWithInt")
@ComponentNamespace("Test/Mock")
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
