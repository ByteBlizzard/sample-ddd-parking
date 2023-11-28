package com.example.dddparking.domain

import java.time.LocalDateTime

class CheckInFailedEvent(
    val plate: Plate,
    val time: LocalDateTime
): DomainEvent