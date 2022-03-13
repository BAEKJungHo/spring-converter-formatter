package com.spring.conversion.propertyEditor

import java.beans.PropertyEditorSupport

class MaxMinPropertyEditor(
    private val min: Int, private val max: Int
): PropertyEditorSupport() {
    override fun getAsText(): String {
        return (this.value as Int).toString()
    }

    override fun setAsText(text: String?) {
        var value = text?.toInt() ?: throw IllegalArgumentException()

        if (value < min) {
            value = min
        } else if (value > max) {
            value = max
        }

        setValue(value)
    }
}