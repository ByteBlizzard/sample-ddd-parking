package com.example.dddparking.domain

import java.time.LocalDateTime

class PaidEvent (
    val plate: Plate,
    val amount: Int,
    val payTime: LocalDateTime
): DomainEvent