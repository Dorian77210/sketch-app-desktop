package com.terbah.sketch.app.core.workflow;

import com.terbah.mock.SketchComponentWithIntParam;
import com.terbah.mock.SketchComponentWithStringParam;
import com.terbah.mock.SketchComponentWithTwoString;
import com.terbah.sketch.app.SpringConfiguration;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import com.terbah.sketch.app.core.injector.SketchDataInjector;
import com.terbah.sketch.app.core.workflow.DefaultSketchComponentWorkflow;
import com.terbah.sketch.app.core.workflow.SketchComponentWorkflow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Dorian TERBAH
 * <p>
 * Test the SketchComponentWorkflow
 * @version 1.0
 */

@SpringBootTest(classes = SpringConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SketchComponentWorkflowTest {

    private SketchComponentWorkflow workflow;

    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    @Autowired
    private SketchComponentWorkflowFactory factory;

    @BeforeEach
    public void registerTestComponents() {
        this.configurationManager.registerComponent(SketchComponentWithStringParam.class);
        this.configurationManager.registerComponent(SketchComponentWithTwoString.class);
        this.configurationManager.registerComponent(SketchComponentWithIntParam.class);
    }

    @BeforeEach
    void setWorkflow() {
        this.workflow = this.factory.createWorkflow();
    }

    @Test
    void testImpossibleCreationOfLink() {
        SketchComponentWithIntParam intComponent = new SketchComponentWithIntParam();
        SketchComponentWithStringParam stringComponent = new SketchComponentWithStringParam();

        assertFalse(this.workflow.existsLinkBetween(intComponent, stringComponent, "invalid"));
        assertFalse(this.workflow.createLinkBetween(intComponent, stringComponent, "data"));
        assertFalse(this.workflow.createLinkBetween(stringComponent, intComponent, "data"));
    }

    /**
     * c --> a - d
     */
    @Test
    void testWellCreationOfSingleLink() {
        SketchComponentWithStringParam c = new SketchComponentWithStringParam();
        SketchComponentWithTwoString d = new SketchComponentWithTwoString();

        assertFalse(this.workflow.existsLinkBetween(c, d, "a"));
        assertFalse(this.workflow.existsLinkBetween(c, d, "b"));
        assertFalse(this.workflow.existsLinkBetween(d, c, "data"));

        assertTrue(this.workflow.createLinkBetween(c, d,"a"));
        assertTrue(this.workflow.existsLinkBetween(c, d, "a"));
        assertFalse(this.workflow.createLinkBetween(c, d,"a"));
        assertFalse(this.workflow.createLinkBetween(d, c, "data"));

        assertTrue(this.workflow.createLinkBetween(c, d, "b"));
        assertTrue(this.workflow.existsLinkBetween(c, d, "b"));
        assertTrue(this.workflow.existsLinkBetween(c, d, "a"));
        assertFalse(this.workflow.createLinkBetween(c, d, "b"));
    }

    @Test
    void testWellExecution() {
        SketchComponentWithStringParam a = new SketchComponentWithStringParam();
        SketchComponentWithTwoString c = new SketchComponentWithTwoString();
        assertTrue(this.workflow.createLinkBetween(a, c, "a"));
        assertTrue(this.workflow.createLinkBetween(a, c, "b"));
        this.workflow.execute(c);
    }
}
