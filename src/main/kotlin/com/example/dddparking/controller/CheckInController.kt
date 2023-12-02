package com.example.dddparking.controller

import com.example.dddparking.domain.*
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

@Controller
class CheckInController(
    private val checkInCommandHandler: CheckInCommandHandler,
    private val commandInvoker: CommandInvoker
) {
    @MutationMapping
    fun checkIn(@Argument("req") req: CheckInReq): Boolean {
        return commandInvoker.invoke {
            checkInCommandHandler.handle(
                it,
                CheckInCommand(
                    plate = Plate(req.plate),
                    checkInTime = LocalDateTime.parse(req.time)
                )
            )
        }
    }
}

class CheckInReq(
    val plate: String,
    val time: String
)