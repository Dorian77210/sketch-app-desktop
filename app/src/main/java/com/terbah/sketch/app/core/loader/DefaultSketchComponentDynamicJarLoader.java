package com.terbah.sketch.app.core.loader;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dorian Terbah
 *
 * @since 1.0
 *
 * This class will dynamically load the jars stored in the libs' dir of the app
 */

@Service
public class DefaultSketchComponentDynamicJarLoader implements SketchComponentDynamicJarLoader {

    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    private Logger logger;

    /**
     * Default constructor of the class <code<SketchComponentDynamicLoader</code>
     */
    DefaultSketchComponentDynamicJarLoader()
    {
        /**
         * Empty constructor
         */
    }

    /**
     * Load dynamically the jars stored in the lib folder of the application
     * @param libDir The folder where the libraries are stored for the application.
     */
    private List<URL> getJarURLS(final String libDir)
    {
        File[] jars = Objects.requireNonNull(new File(libDir).listFiles());
        List<URL> urls = new ArrayList<>();
        for (File jar : jars) {
            try {
                urls.add(jar.toURI().toURL());
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }
        return urls;
    }

    @PostConstruct
    public void init() {
        this.logger = SketchLoggerManager.getLogger(this.getClass());
    }

    @Override
    public void loadComponents() {
        // todo: change raw dir
        String libDir = "D:\\libs";
        List<URL> urls = this.getJarURLS(libDir);

        URLClassLoader childClassLoader = new URLClassLoader(urls.toArray(new URL[0]), ClassLoader.getSystemClassLoader());
        ServiceLoader<SketchComponent> loader = ServiceLoader.load(SketchComponent.class, childClassLoader);
        for(SketchComponent<?> component : loader)
        {
            Class<? extends SketchComponent<?>> componentClass = (Class<? extends SketchComponent<?>>) component.getClass();
            this.logger.log(Level.FINE, "Register component {0}", componentClass);
            this.configurationManager.registerComponent(componentClass);
        }
    }

}
