package com.terbah.sketch.app.core;

import com.terbah.sketch.api.SketchComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DefaultSketchComponentWorkflow implements SketchComponentWorkflow {

    private Logger logger;

    public DefaultSketchComponentWorkflow() {

    }

    @Autowired
    public void setLogger() {
        this.logger = Logger.getLogger(this.getClass().getCanonicalName());
    }

    @Override
    public void insertComponent(SketchComponent<?> component) {
        this.logger.log(Level.CONFIG, "Un message de debug");

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
}
