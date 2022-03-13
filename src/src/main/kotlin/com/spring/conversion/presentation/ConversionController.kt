package com.spring.conversion.presentation

import com.spring.conversion.domain.Level
import com.spring.conversion.propertyEditor.LevelPropertyEditor
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset

@RestController
class ConversionController {

    @InitBinder
    fun initBinder(webDataBinder: WebDataBinder) {
        webDataBinder.registerCustomEditor(Level::class.java, LevelPropertyEditor())
    }

    /** endPoint /charset?charset=UTF-8 */
    @GetMapping("/charset")
    fun charsetEditor(@RequestParam charset: Charset): String {
        return charset.toString()
    }

    @GetMapping("/level")
    fun levelCustomEditor(@RequestParam level: Level): Int {
        return level.intValue()
    }
}