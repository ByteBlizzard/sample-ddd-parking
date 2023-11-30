package com.example.dddparking

import com.example.dddparking.domain.*
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

@Controller
class CheckOutController(
    private val checkOutCommandHandler: CheckOutCommandHandler,
    private val domainEventDispatcher: DomainEventDispatcher
) {
    @MutationMapping
    fun checkOut(@Argument("req") req: CheckOutReq): Boolean {
        val eventQueue = SimpleEventQueue()
        val result = checkOutCommandHandler.handle(
            eventQueue,
            CheckOutCommand(
                plate = Plate(req.plate),
                time = LocalDateTime.parse(req.time)
            )
        )

        this.domainEventDispatcher.dispatchNow(eventQueue)

        return result
    }
}

class CheckOutReq(
    val plate: String,
    val time: String
)