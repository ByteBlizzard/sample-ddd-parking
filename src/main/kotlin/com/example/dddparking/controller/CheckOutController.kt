package com.example.dddparking.controller

import com.example.dddparking.domain.*
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

@Controller
class CheckOutController(
    private val checkOutCommandHandler: CheckOutCommandHandler,
    private val commandInvoker: CommandInvoker
) {
    @MutationMapping
    fun checkOut(@Argument("req") req: CheckOutReq): Boolean {
        return commandInvoker.invoke { eventQueue ->
            checkOutCommandHandler.handle(
                eventQueue,
                CheckOutCommand(
                    plate = Plate(req.plate),
                    time = LocalDateTime.parse(req.time)
                )
            )
        }
    }
}

class CheckOutReq(
    val plate: String,
    val time: String
)