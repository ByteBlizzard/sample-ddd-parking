package com.example.dddparking.domain

import java.time.LocalDateTime

class CheckedInEvent(
    val plate: Plate,
    val time: LocalDateTime
): DomainEvent