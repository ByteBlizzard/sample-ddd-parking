package com.example.dddparking

import com.example.dddparking.domain.*
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

@Controller
class CheckInController(
    private val checkInCommandHandler: CheckInCommandHandler,
    private val domainEventDispatcher: DomainEventDispatcher
) {
    @MutationMapping
    fun checkIn(@Argument("req") req: CheckInReq): Boolean {
        val eventQueue = SimpleEventQueue()
        val result = checkInCommandHandler.handle(
            eventQueue,
            CheckInCommand(
                plate = Plate(req.plate),
                checkInTime = LocalDateTime.parse(req.time)
            )
        )

        this.domainEventDispatcher.dispatchNow(eventQueue)

        return result
    }
}

class CheckInReq(
    val plate: String,
    val time: String
)