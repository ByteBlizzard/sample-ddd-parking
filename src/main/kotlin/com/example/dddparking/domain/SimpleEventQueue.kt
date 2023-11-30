package com.example.dddparking.domain

import java.util.LinkedList

class SimpleEventQueue: EventQueue {
    private val queue: MutableList<DomainEvent> = LinkedList()

    override fun enqueue(event: DomainEvent) {
        queue.add(event)
    }

    override fun queue(): List<DomainEvent> {
        return queue
    }
}