package com.example.dddparking.domain

import java.time.LocalDateTime

class CheckOutFailedEvent(
    val plate: Plate,
    val time: LocalDateTime,
    val message: String
): DomainEvent