package com.terbah;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.terbah.sketch.app.core.config",
        "com.terbah.sketch.app.core.workflow",
        "com.terbah.sketch.app.core.injector",
        "com.terbah.sketch.app.ui.model"
})
public class SpringTestConfiguration {
}
