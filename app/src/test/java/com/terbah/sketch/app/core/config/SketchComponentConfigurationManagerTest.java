package com.terbah.sketch.app.core.config;

import com.terbah.mock.SketchComponentWithStringParam;
import com.terbah.mock.SketchComponentWithTwoString;
import com.terbah.mock.SketchInvalidComponent;
import com.terbah.sketch.app.SpringConfiguration;
import com.terbah.sketch.app.core.injector.SketchDataInjector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringConfiguration.class)
public class SketchComponentConfigurationManagerTest {

    @Autowired
    public SketchDataInjector injector;

    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    @BeforeEach
    public void setupManager() {
        this.configurationManager.registerComponent(SketchComponentWithStringParam.class);
        this.configurationManager.registerComponent(SketchComponentWithTwoString.class);
        this.configurationManager.registerComponent(SketchInvalidComponent.class);
    }

    @Test
    public void testWellInsertionOfComponents() {
        SketchComponentConfiguration configuration = this.configurationManager.getConfigurationByComponentClass(SketchComponentWithStringParam.class);
        assertNotNull(configuration);
        assertEquals(String.class, configuration.getReturnType());
        assertTrue(configuration.hasMethodInjectableFor("data"));
        assertFalse(configuration.hasMethodInjectableFor("b"));
        assertEquals("SketchComponentWithString", configuration.getComponentName());
        assertEquals("Test/Mock", configuration.getNamespace());

        configuration = this.configurationManager.getConfigurationByComponentClass(SketchComponentWithTwoString.class);
        assertNotNull(configuration);
        assertEquals(String.class, configuration.getReturnType());
        assertTrue(configuration.hasMethodInjectableFor("a"));
        assertTrue(configuration.hasMethodInjectableFor("b"));
        assertFalse(configuration.hasMethodInjectableFor("data"));
        assertEquals("SketchComponentWithTwoString", configuration.getComponentName());
        assertEquals("Test/Mock", configuration.getNamespace());

        configuration = this.configurationManager.getConfigurationByComponentClass(SketchInvalidComponent.class);
        assertNull(configuration);

        assertTrue(false);
    }
}
