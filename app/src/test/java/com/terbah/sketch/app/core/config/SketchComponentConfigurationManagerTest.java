package com.terbah.sketch.app.core.config;

import com.terbah.mock.SketchComponentWithStringParam;
import com.terbah.mock.SketchComponentWithTwoString;
import com.terbah.sketch.app.SpringConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringConfiguration.class)
public class SketchComponentConfigurationManagerTest {

    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    @BeforeEach
    public void setupManager() {
        this.configurationManager.registerComponent(SketchComponentWithStringParam.class);
        this.configurationManager.registerComponent(SketchComponentWithTwoString.class);
    }

    @Test
    public void testWellInsertionOfComponents() {
        Optional<SketchComponentConfiguration> configurationOptional = this.configurationManager.getConfigurationByComponentClass(SketchComponentWithStringParam.class);
        assertTrue(configurationOptional.isPresent());
        SketchComponentConfiguration configuration = configurationOptional.get();
        assertEquals(String.class, configuration.getReturnType());
        assertTrue(configuration.hasMethodInjectableFor("data"));
        assertFalse(configuration.hasMethodInjectableFor("b"));


        configurationOptional = this.configurationManager.getConfigurationByComponentClass(SketchComponentWithTwoString.class);
        assertTrue(configurationOptional.isPresent());
        configuration = configurationOptional.get();
        assertEquals(String.class, configuration.getReturnType());
        assertTrue(configuration.hasMethodInjectableFor("a"));
        assertTrue(configuration.hasMethodInjectableFor("b"));
        assertFalse(configuration.hasMethodInjectableFor("data"));
    }
}
