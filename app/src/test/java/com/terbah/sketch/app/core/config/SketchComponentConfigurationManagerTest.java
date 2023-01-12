package com.terbah.sketch.app.core.config;

import com.terbah.SpringTestConfiguration;
import com.terbah.mock.SketchComponentWithStringParam;
import com.terbah.mock.SketchComponentWithTwoString;
import com.terbah.mock.SketchInvalidComponent;
import com.terbah.sketch.app.core.injector.SketchDataInjector;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringTestConfiguration.class)
class SketchComponentConfigurationManagerTest {

    @Autowired
    private SketchDataInjector injector;

    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    @BeforeEach
    void setupManager() {
        this.configurationManager.registerComponent(SketchComponentWithStringParam.class);
        this.configurationManager.registerComponent(SketchComponentWithTwoString.class);
        this.configurationManager.registerComponent(SketchInvalidComponent.class);
    }

    @Test
    void testWellInsertionOfComponents() {
        SketchComponentConfiguration configuration = this.configurationManager.getConfigurationByComponentClass(SketchComponentWithStringParam.class);
        assertNotNull(configuration);
        assertEquals(String.class, configuration.getReturnType());
        assertTrue(configuration.hasMethodInjectableFor("data"));
        assertFalse(configuration.hasMethodInjectableFor("b"));
        assertEquals("SketchComponentWithStringParam", configuration.getComponentName());
        assertEquals("test", configuration.getNamespace());

        configuration = this.configurationManager.getConfigurationByComponentClass(SketchComponentWithTwoString.class);
        assertNotNull(configuration);
        assertEquals(String.class, configuration.getReturnType());
        assertTrue(configuration.hasMethodInjectableFor("a"));
        assertTrue(configuration.hasMethodInjectableFor("b"));
        assertFalse(configuration.hasMethodInjectableFor("data"));
        assertEquals("SketchComponentWithTwoString", configuration.getComponentName());
        assertEquals("test", configuration.getNamespace());
    }
}
