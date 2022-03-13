package com.spring.conversion.advice

import com.spring.conversion.propertyEditor.MaxMinPropertyEditor
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.support.WebBindingInitializer

/**
 * 모든 컨트롤러에 전역적으로 적용되게 initBinder 설정
 * WebBindingInitializer 구현체가 동작하려면 빈으로 등록되어야 한다.
 * 또한, AnnotationMethodHandlerAdapter 도 빈으로 직접 등록해야 한다.
 */
class GlobalWebBindingInitializerAdvice: WebBindingInitializer {

    override fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(Int::class.java, "age", MaxMinPropertyEditor(0, 200))
    }
}