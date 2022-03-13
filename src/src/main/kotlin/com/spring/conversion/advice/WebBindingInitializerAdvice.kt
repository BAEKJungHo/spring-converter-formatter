package com.spring.conversion.advice

import com.spring.conversion.domain.Level
import com.spring.conversion.propertyEditor.LevelPropertyEditor
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.support.WebBindingInitializer

/**
 * @ControllerAdvice 와 @InitBinder 를 사용하여 직접 구현
 */
@ControllerAdvice
class WebBindingInitializerAdvice: WebBindingInitializer {

    @InitBinder
    override fun initBinder(binder: WebDataBinder) {
//        binder.registerCustomEditor(Level::class.java, LevelPropertyEditor())
    }
}