package com.example.dddparking.domain

class Plate(val value: String) {
    override fun equals(other: Any?): Boolean {
        return other != null && other is Plate && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}