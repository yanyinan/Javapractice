package com.satrt.springweb.core.utils.model;

import com.satrt.springweb.core.constant.Constant;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/28 15:04
 */
@Component
public class ResourceLoader implements org.springframework.core.io.ResourceLoader {
    @Override
    public Resource getResource(String location) {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }
}
