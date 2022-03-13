package com.spring.conversion.domain

enum class Level(private val value: Int) {
    GOLD(3), SILVER(2), BRONZE(1);

    fun intValue(): Int {
        return value
    }

    companion object {
        fun valueOf(value: Int): Level {
            return when (value) {
                GOLD.value -> GOLD
                SILVER.value -> SILVER
                BRONZE.value -> BRONZE
                else -> throw IllegalArgumentException("Unknown Level")
            }
        }
    }
}