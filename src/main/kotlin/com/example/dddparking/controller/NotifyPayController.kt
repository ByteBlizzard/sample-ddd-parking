package com.example.dddparking.controller

import com.example.dddparking.domain.*
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

@Controller
class NotifyPayController(
    private val notifyPayCommandHandler: NotifyPayCommandHandler,
    private val commandInvoker: CommandInvoker
) {
    @MutationMapping
    fun notifyPay(@Argument("req") req: NotifyPayReq): Boolean {
        return commandInvoker.invoke { eventQueue ->
            notifyPayCommandHandler.handle(
                eventQueue,
                NotifyPayCommand(
                    plate = Plate(req.plate),
                    payTime = LocalDateTime.parse(req.time),
                    amount = req.amount
                )
            )
            true
        }
    }
}

class NotifyPayReq (
    val plate: String,
    val time: String,
    val amount: Int
)