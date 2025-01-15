package com.example.adocao_pet.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer{
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http:localhost:5500","http://127.0.0.1..5500","null")
            .allowedMethods("GET","POST","PUT","DELETE","PATCH")
            .allowedHeaders("*")
            .allowCredentials(true)

    }
}