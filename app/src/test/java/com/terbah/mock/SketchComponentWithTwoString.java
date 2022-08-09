package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentName;
import com.terbah.sketch.api.annotation.ComponentNamespace;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;

@ComponentName("SketchComponentWithTwoString")
@ComponentNamespace("Test/Mock")
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
    public String execute() throws SketchComponentExecuteException {
        if (this.a == null || this.b == null) {
            throw new SketchComponentExecuteException("The two strings are null");
        }

        return this.a + this.b;
    }

    @Override
    public SketchComponent<String> copy() {
        return new SketchComponentWithTwoString();
    }
}
