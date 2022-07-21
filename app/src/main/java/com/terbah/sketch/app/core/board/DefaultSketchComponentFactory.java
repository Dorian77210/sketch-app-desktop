package com.terbah.sketch.app.core.board;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Dorian TERBAH
 *
 * Default implementation of the SketchComponentFactory.
 *
 * @since 1.0
 */
@Service
class DefaultSketchComponentFactory implements SketchComponentFactory {

    /**
     * Logger of the factory.
     */
    private Logger logger;

    @PostConstruct
    private void init() {
        this.logger = SketchLoggerManager.getLogger(this.getClass());
    }

    @Override
    public SketchComponent<?> createComponent(Class<? extends SketchComponent<?>> componentClass) {
        try {
            Constructor<? extends SketchComponent<?>> constructor = componentClass.getConstructor();
            return constructor.newInstance();
        } catch (Exception exception) {
            this.logger.log(Level.SEVERE, "Error during the creation of component", exception);
            return null;
        }
    }
}
