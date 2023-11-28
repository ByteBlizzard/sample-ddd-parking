package com.example.dddparking.domain

interface DomainEventListener {
    fun onEvent(event: DomainEvent)
}