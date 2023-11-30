package com.example.dddparking.domain

import org.springframework.stereotype.Component

interface DomainEventDispatcher {
    fun dispatchNow(eventQueue: EventQueue)
}

@Component
class SyncDomainEventDispatcher(
    private val listeners: List<DomainEventListener>
) : DomainEventDispatcher {
    override fun dispatchNow(eventQueue: EventQueue) {
        eventQueue.queue().forEach { event ->
            listeners.forEach { listener ->
                listener.onEvent(event)
            }
        }
    }
}