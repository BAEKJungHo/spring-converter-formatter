package com.spring.conversion.propertyEditor

import com.spring.conversion.domain.Level
import java.beans.PropertyEditorSupport

class LevelPropertyEditor: PropertyEditorSupport() {

    override fun getAsText(): String {
        // this.value -> setValue 에 의해 저장된 Level 타입의 오브젝트를 가져와서 값을 문자로 변환한다.
        return ((this.value as Level).intValue().toString())
    }

    override fun setAsText(text: String?) {
        this.value = text?.trim()?.let { Level.valueOf(it.toInt()) }
    }
}