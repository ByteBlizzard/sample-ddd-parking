package com.example.dddparking.domain

import java.time.LocalDateTime

class CheckedOutEvent(
    val plate: Plate,
    val time: LocalDateTime
): DomainEvent