package com.example.dddparking.domain

import org.springframework.stereotype.Component
import java.time.LocalDateTime

class CalculateFeeCommand(
    val plate: Plate,
    val time: LocalDateTime
)

@Component
class CalculateFeeCommandHandler(
    private val parkingRepository: ParkingRepository
) {
    fun handle(command: CalculateFeeCommand): Int {
        val parking = this.parkingRepository.findByIdOrError(command.plate)
        return parking.calculateFeeNow(command.time)
    }
}