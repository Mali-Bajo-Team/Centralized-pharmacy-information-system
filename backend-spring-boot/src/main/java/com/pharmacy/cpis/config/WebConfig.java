package com.pharmacy.cpis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // Enable CORS to avoid @CorssOrigin annotation on every controller
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //TODO: Make separated file for this type of config
        String FRONT_PORT = "8082";
        registry.addMapping("/**").allowedOrigins("http://localhost:"+FRONT_PORT);
    }
}