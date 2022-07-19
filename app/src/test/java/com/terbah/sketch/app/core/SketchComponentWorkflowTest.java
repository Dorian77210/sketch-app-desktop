package com.terbah.sketch.app.core;

import com.terbah.mock.SketchComponentWithStringParam;
import com.terbah.sketch.app.SpringConfiguration;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import com.terbah.sketch.app.core.workflow.SketchComponentWorkflow;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    @BeforeEach
    public void registerTestComponents() {
        this.configurationManager.registerComponent(SketchComponentWithStringParam.class);
    }

    @Test
    public void testCreateLink() {
        this.workflow.insertComponent(null);
    }
}
