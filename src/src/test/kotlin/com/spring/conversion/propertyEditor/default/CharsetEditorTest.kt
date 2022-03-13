package com.spring.conversion.propertyEditor.default

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.propertyeditors.CharsetEditor
import java.nio.charset.Charset

internal class CharsetEditorTest: BehaviorSpec({
    given("CharsetEditor 가 주어지고") {
        val charsetEditor = CharsetEditor()
        `when`("값을 UTF-8 로 설정하면") {
            charsetEditor.setAsText("UTF-8")
            then("값은 문자열이 아닌 Charset 오브젝트이다") {
                charsetEditor.value shouldBe Charset.forName("UTF-8")
            }
        }
    }
})
