package com.mervyn.springboot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by mengran.gao on 2017/11/20.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 在此处定义的静态资源访问的时候需加上/mystatic路径
        registry.addResourceHandler("/mystatic/**")
                .addResourceLocations("classpath:/mystatic/");
        super.addResourceHandlers(registry);
    }
}
