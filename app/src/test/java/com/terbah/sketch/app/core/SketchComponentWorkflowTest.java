package com.terbah.sketch.app.core;

import com.terbah.sketch.app.SpringConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Dorian TERBAH
 * <p>
 * Test the SketchComponentWorkflow
 * @version 1.0
 */

@SpringBootTest(classes = SpringConfiguration.class)
public class SketchComponentWorkflowTest {

    @Autowired
    private SketchComponentWorkflow workflow;

    @Test
    public void testCreateLink() {
        this.workflow.insertComponent(null);
    }
}
