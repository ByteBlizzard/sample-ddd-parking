package com.example.dddparking.controller

import com.example.dddparking.domain.CalculateFeeCommand
import com.example.dddparking.domain.CalculateFeeCommandHandler
import com.example.dddparking.domain.CommandInvoker
import com.example.dddparking.domain.Plate
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

@Controller
class CalculateFeeController(
    private val commandInvoker: CommandInvoker,
    private val calculateFeeCommandHandler: CalculateFeeCommandHandler
) {
    @QueryMapping
    fun shouldPay(@Argument("req") req: ShouldPayReq): Int {
        return commandInvoker.invoke {
            calculateFeeCommandHandler.handle(CalculateFeeCommand(
                plate = Plate(req.plate),
                time = LocalDateTime.parse(req.time)
            ))
        }
    }

    class ShouldPayReq (
        val plate: String,
        val time: String
    )
}