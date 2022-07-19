package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.MethodInjectable;

public class SketchComponentWithStringParam implements SketchComponent<String> {

    private String data;

    public SketchComponentWithStringParam() {
        this.data = "";
    }

    @MethodInjectable("data")
    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String execute() {
        return this.data + " " + this.data;
    }
}
