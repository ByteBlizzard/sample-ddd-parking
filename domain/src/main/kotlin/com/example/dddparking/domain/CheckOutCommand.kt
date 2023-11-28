package com.example.dddparking.domain

import org.springframework.stereotype.Component
import java.time.LocalDateTime

class CheckOutCommand(
    val plate: Plate,
    val time: LocalDateTime
)

@Component
class CheckOutCommandHandler(
    private val parkingRepository: ParkingRepository
) {

    fun handle(eventQueue: EventQueue, command: CheckOutCommand): Boolean {
        val parking = parkingRepository.findByIdOrError(command.plate)
        val result = parking.handle(eventQueue, command)
        parkingRepository.save(parking)

        return result
    }

}