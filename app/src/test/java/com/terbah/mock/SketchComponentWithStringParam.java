package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentName;
import com.terbah.sketch.api.annotation.ComponentNamespace;
import com.terbah.sketch.api.annotation.MethodInjectable;

@ComponentName("SketchComponentWithString")
@ComponentNamespace("Test/Mock")
public class SketchComponentWithStringParam implements SketchComponent<String> {

    private String data;

    public SketchComponentWithStringParam() {
        this.data = "data test";
    }

    @MethodInjectable("data")
    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String execute() {
        return this.data + " " + this.data;
    }

    @Override
    public SketchComponent<String> copy() {
        return new SketchComponentWithStringParam();
    }
}
