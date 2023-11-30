package com.example.dddparking.domain

interface EventQueue {
    fun enqueue(event: DomainEvent)

    fun queue(): List<DomainEvent>
}