package com.example.webdev.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }


//  @Override
//  public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/**");
////            .allowCredentials(true)
////            .allowedOrigins("http://localhost:3000")
////            .allowedHeaders("*")
////            .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD");
//
//  }





}