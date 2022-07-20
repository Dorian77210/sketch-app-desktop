package com.terbah.sketch.app.core.injector;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.config.SketchComponentConfiguration;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dorian TERBAH
 *
 * This class permits to manage the data injection on the components.
 *
 * @version 1.0
 */
@Service
public class SketchDataInjector {

    @Autowired
    private SketchComponentConfigurationManager manager;

    /**
     * Logger for the class.
     */
    private Logger logger;

    @Autowired
    public void setLogger() {
        this.logger = SketchLoggerManager.getLogger(this.getClass());
    }

    /**
     * Inject the data in the component with the corresponding entry name.
     *
     * @param component The component
     * @param entryName The name of the entry in the component
     *                  where the data will be injected
     * @param data The data to inject.
     * @return <code>true</code> if the data is correctly injected, else <code>false</code>.
     */
    public boolean injectData(SketchComponent<?> component, String entryName, Object data) {
        SketchComponentConfiguration configuration = this.manager.getConfigurationByComponentClass(component.getClass());
        Optional<SketchComponentConfiguration.SketchInjectableMethod> methodInjectable = configuration.getMethodInjectable(entryName);
        if (methodInjectable.isPresent()) {
            Method method = methodInjectable.get().getMethod();
            try {
                method.invoke(component, data);
            } catch (Exception exception) {
                this.logger.log(Level.SEVERE, "Error during data injection in the component {}", component);
                return false;
            }
        }

        return true;
    }
}
