package com.example.dddparking.domain

interface AlarmService {
    fun alarm(plate: Plate, message: String)
}