package com.spring.conversion.converter

import com.spring.conversion.domain.Level
import org.springframework.core.convert.converter.Converter

class StringToLevelConverter: Converter<String, Level> {
    override fun convert(source: String): Level {
        return Level.valueOf(source.toInt())
    }
}