package com.example.dddparking.domain

import org.springframework.stereotype.Component
import java.time.LocalDateTime

class CheckInCommand(
    val plate: Plate,
    val checkInTime: LocalDateTime
)

@Component
class CheckInCommandHandler(
    private val parkingRepository: ParkingRepository
) {
    fun handle(eventQueue: EventQueue, command: CheckInCommand): Boolean {
        val parking = parkingRepository.findByIdOrError(command.plate)
        val result = parking.handle(eventQueue, command)
        parkingRepository.save(parking)

        return result
    }
}