package com.example.dddparking.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

interface CommandInvoker {
    fun <R> invoke(run: (EventQueue) -> R): R
}

@Component
class OneTransactionCommandInvoker(
    transactionManager: PlatformTransactionManager,
    private val domainEventDispatcher: DomainEventDispatcher
) : CommandInvoker {
    private val transactionTemplate: TransactionTemplate = TransactionTemplate(transactionManager)

    override fun <R> invoke(run: (EventQueue) -> R): R {
        return transactionTemplate.execute { s ->
            val eventQueue = SimpleEventQueue()
            val result = run(eventQueue)
            this.domainEventDispatcher.dispatchNow(eventQueue)

            return@execute result
        } ?: throw IllegalStateException()

    }

}