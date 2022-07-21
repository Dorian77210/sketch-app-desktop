package com.terbah.sketch.app.core.board;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.config.SketchComponentConfiguration;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import com.terbah.sketch.app.ui.board.SketchComponentUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dorian TERBAH
 *
 * Default implementation of the SketchComponentUIFactory.
 *
 * @since 1.0
 */
@Service
class DefaultSketchComponentUIFactory implements SketchComponentUIFactory {

    /**
     * Configuration manager of the app.
     */
    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    @Override
    public SketchComponentUI createUIFor(SketchComponent<?> component) {
        SketchComponentConfiguration configuration = this.configurationManager.getConfigurationByComponentClass(component.getClass());
        return new SketchComponentUI(configuration);
    }
}
