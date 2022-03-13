package com.spring.conversion

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConversionApplication

fun main(args: Array<String>) {
    runApplication<ConversionApplication>(*args)
}
