package com.example.dddparking.db

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "alarm")
class AlarmTable(
    @Id
    @GeneratedValue
    var id: Long?,

    val plate: String,

    val msg: String,

    val time: LocalDateTime
)