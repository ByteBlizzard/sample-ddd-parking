package com.example.dddparking

import com.example.dddparking.domain.*
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

@Controller
class NotifyPayController(
    private val notifyPayCommandHandler: NotifyPayCommandHandler,
    private val domainEventDispatcher: DomainEventDispatcher
) {
    @MutationMapping
    fun notifyPay(@Argument("req") req: NotifyPayReq): Boolean {
        val eventQueue = SimpleEventQueue()
        notifyPayCommandHandler.handle(
            eventQueue,
            NotifyPayCommand(
                plate = Plate(req.plate),
                payTime = LocalDateTime.parse(req.time),
                amount = req.amount
            )
        )

        this.domainEventDispatcher.dispatchNow(eventQueue)

        return true
    }
}

class NotifyPayReq (
    val plate: String,
    val time: String,
    val amount: Int
)