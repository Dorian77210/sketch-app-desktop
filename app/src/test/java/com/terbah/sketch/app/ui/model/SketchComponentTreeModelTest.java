package com.terbah.sketch.app.ui.model;

import com.terbah.mock.SketchComponentWithIntParam;
import com.terbah.mock.SketchComponentWithStringParam;
import com.terbah.mock.SketchComponentWithTwoString;
import com.terbah.sketch.app.SpringConfiguration;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringConfiguration.class)
public class SketchComponentTreeModelTest {

    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    @Autowired
    private SketchComponentTreeModel treeModel;

    @BeforeEach
    void clearTreeModel() {
        this.treeModel.clear();
        this.configurationManager.registerComponent(SketchComponentWithIntParam.class);
        this.configurationManager.registerComponent(SketchComponentWithStringParam.class);
        this.configurationManager.registerComponent(SketchComponentWithTwoString.class);
    }

    @Test
    void testInsertOne() {
        this.treeModel.insertComponent(SketchComponentWithIntParam.class);
        assertTrue(this.treeModel.containsComponent(SketchComponentWithIntParam.class));
    }

    @Test
    void testInsertMany() {
        this.treeModel.insertComponent(SketchComponentWithIntParam.class);
        this.treeModel.insertComponent(SketchComponentWithStringParam.class);

        assertTrue(this.treeModel.containsComponent(SketchComponentWithIntParam.class));
        assertTrue(this.treeModel.containsComponent(SketchComponentWithStringParam.class));
        assertFalse(this.treeModel.containsComponent(SketchComponentWithTwoString.class));
    }
}
