package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.MethodInjectable;

public class SketchComponentWithTwoString implements SketchComponent<String> {

    private String b;
    private String a;

    public SketchComponentWithTwoString() {
        this.a = "";
        this.b = "";
    }

    @MethodInjectable("a")
    public void setA(String a) {
        this.a = a;
    }

    @MethodInjectable("b")
    public void setB(String b) {
        this.b = b;
    }

    @Override
    public String execute() {
        return this.a + this.b;
    }
}
