package com.spring.conversion.presentation

import com.spring.conversion.domain.Level
import com.spring.conversion.domain.Member
import com.spring.conversion.propertyEditor.LevelPropertyEditor
import com.spring.conversion.propertyEditor.MaxMinPropertyEditor
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset

@RestController
class ConversionController {

    @InitBinder
    fun initBinder(webDataBinder: WebDataBinder) {
//        webDataBinder.registerCustomEditor(Int::class.java, "age", MaxMinPropertyEditor(0, 200))
    }

    /** endPoint /charset?charset=UTF-8 */
    @GetMapping("/charset")
    fun charsetEditor(@RequestParam charset: Charset): String {
        return charset.toString()
    }

    /** endPoint /level?level=3 */
    @GetMapping("/level")
    fun levelCustomEditor(@RequestParam level: Level): Int {
        return level.intValue()
    }

    /** endPoint /member?id=1&age=1000 */
    @GetMapping("/member")
    fun maxMinEditor(@ModelAttribute member: Member): Int {
        // return 200
        return member.age
    }
}