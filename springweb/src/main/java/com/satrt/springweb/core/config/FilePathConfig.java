package com.satrt.springweb.core.config;

import com.satrt.springweb.core.constant.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置文件路径
 * @author 26481
 */
@Configuration
public class FilePathConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/web/**")
                .addResourceLocations("file:" + Constant.UPLOAD_PATH);
    }


}
