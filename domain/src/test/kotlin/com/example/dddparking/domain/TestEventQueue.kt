package com.example.dddparking.domain

import java.util.LinkedList

class TestEventQueue: EventQueue {
    val list = LinkedList<DomainEvent>()
    override fun enqueue(event: DomainEvent) {
        list.add(event)
    }
}