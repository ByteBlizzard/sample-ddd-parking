package com.example.dddparking.domain

import org.springframework.stereotype.Component
import java.time.LocalDateTime

class NotifyPayCommand(
    val plate: Plate,
    val amount: Int,
    val payTime: LocalDateTime
)

@Component
class NotifyPayCommandHandler(
    private val parkingRepository: ParkingRepository
) {

    fun handle(eventQueue: EventQueue, command: NotifyPayCommand) {
        val parking = parkingRepository.findByIdOrError(command.plate)
        parking.handle(eventQueue, command)
        parkingRepository.save(parking)
    }

}