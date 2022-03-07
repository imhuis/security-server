package com.imhuis.server.core.context;

import com.imhuis.server.config.HttpSessionConfig;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * @author: imhuis
 * @date: 2022/1/14
 * @description: Java Servlet Container Initialization
 */
public class Initializer extends AbstractHttpSessionApplicationInitializer {

    public Initializer() {
        super(HttpSessionConfig.class);
    }
}
