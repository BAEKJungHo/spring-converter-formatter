package com.spring.conversion.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset

@RestController
class ConversionController {

    /** endPoint /charset?charset=UTF-8 */
    @GetMapping("/charset")
    fun charsetEditor(@RequestParam charset: Charset): String {
        return charset.toString()
    }
}