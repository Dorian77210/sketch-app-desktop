package com.terbah.sketch.app.core.workflow;

import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import com.terbah.sketch.app.core.injector.SketchDataInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SketchComponentWorkflowFactory {

    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    @Autowired
    private SketchDataInjector injector;

    public SketchComponentWorkflowFactory() {
        /**
         * Constructor empty because all the attributes are autowired
         */
    }

    public SketchComponentWorkflow createWorkflow() {
        return new DefaultSketchComponentWorkflow(this.configurationManager, this.injector);
    }
}
