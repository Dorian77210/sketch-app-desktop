package com.terbah.mock;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.annotation.ComponentConfiguration;
import com.terbah.sketch.api.annotation.MethodInjectable;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;

@ComponentConfiguration(
        namespace = "test",
        name = "SketchComponentWithTwoString"
)
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

    @Override
    public SketchConfigurationPopup openConfigurationPopup() {
        return null;
    }
}
