package com.terbah.sketch.natif.component.common;


import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;


@ComponentConfiguration(
        name = "Number input",
        namespace = "Common"
)
public class SketchNumberComponent implements SketchComponent<Number> {

    private Number value;

    public SketchNumberComponent() {
        this.value = 0;
    }

    @Override
    public Number execute() {
        return this.value;
    }

    @Override
    public SketchComponent<Number> copy() {
        SketchNumberComponent component = new SketchNumberComponent();
        component.value = this.value;
        return component;
    }
}
