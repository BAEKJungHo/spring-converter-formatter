package com.spring.conversion.converter

import com.spring.conversion.domain.Level
import org.springframework.core.convert.converter.Converter

class LevelToStringConverter: Converter<Level, String> {
    override fun convert(source: Level): String {
        return source.intValue().toString()
    }
}