package com.example.dddparking.db

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class AlarmTable(
    @Id
    @GeneratedValue
    var id: Long?,

    val plate: String,

    val msg: String,

    val time: LocalDateTime
)