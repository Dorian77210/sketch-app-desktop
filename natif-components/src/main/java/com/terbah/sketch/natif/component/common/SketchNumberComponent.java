package com.terbah.sketch.natif.component.common;


import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentName;
import com.terbah.sketch.api.annotation.ComponentNamespace;

@ComponentName("Number input")
@ComponentNamespace("Common")
public class SketchNumberComponent implements SketchComponent<Number> {

    @Override
    public Number execute() {
        return 10;
    }
}
