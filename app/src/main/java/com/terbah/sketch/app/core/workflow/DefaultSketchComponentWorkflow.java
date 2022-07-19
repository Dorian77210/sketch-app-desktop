package com.terbah.sketch.app.core.workflow;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DefaultSketchComponentWorkflow implements SketchComponentWorkflow {
    
    private final List<SketchComponentEdge> edges;

    private final List<SketchComponent<?>> orphanComponents;

    @Autowired
    public SketchComponentConfigurationManager configurationManager;

    /**
     * Logger used in the workflow.
     */
    private Logger logger;

    public DefaultSketchComponentWorkflow() {
        this.edges = new ArrayList<>();
        this.orphanComponents = new ArrayList<>();
    }

    @Autowired
    public void setLogger() {
        this.logger = SketchLoggerManager.getLogger(this.getClass());
    }

    @Override
    public void insertComponent(SketchComponent<?> component) {
    }

    @Override
    public boolean execute(SketchComponent<?> component) {
        return false;
    }

    @Override
    public boolean createLinkBetween(SketchComponent<?> from, SketchComponent<?> to, String entryName) {
        return false;
    }

    @Override
    public boolean existsLinkBetween(SketchComponent<?> from, SketchComponent<?> to, String entryName) {
        return false;
    }

    private static class SketchComponentEdge {
        private final SketchComponent<?> parentComponent;

        private final SketchComponent<?> childComponent;

        private final String childComponentEntry;

        private SketchComponentEdge(SketchComponent<?> parentComponent, SketchComponent<?> childComponent, String childComponentEntry) {
            this.parentComponent = parentComponent;
            this.childComponent = childComponent;
            this.childComponentEntry = childComponentEntry;
        }
    }
}
