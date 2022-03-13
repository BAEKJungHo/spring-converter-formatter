package com.spring.conversion.config

import com.spring.conversion.converter.LevelToStringConverter
import com.spring.conversion.converter.StringToLevelConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToLevelConverter())
        registry.addConverter(LevelToStringConverter())
    }
}