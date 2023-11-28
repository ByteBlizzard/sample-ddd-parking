package com.example.dddparking.domain

import java.time.Duration
import java.time.LocalDateTime

interface Parking {
    fun handle(eventQueue: EventQueue, command: CheckInCommand): Boolean
    fun calculateFeeNow(now: LocalDateTime): Int
    fun handle(eventQueue: EventQueue, command: NotifyPayCommand)
    fun handle(eventQueue: EventQueue, command: CheckOutCommand): Boolean
}

class ParkingImpl(
    private val id: Plate,
    private var checkInTime: LocalDateTime?,
    private var lastPlayTime: LocalDateTime?,
    private var totalPaid: Int = 0
): Parking {
    override fun handle(eventQueue: EventQueue, command: CheckInCommand): Boolean {
        if (inPark()) {
            eventQueue.enqueue(CheckInFailedEvent(id, command.checkInTime))
            return false
        }

        eventQueue.enqueue(CheckedInEvent(id, command.checkInTime))
        this.checkInTime = command.checkInTime
        return true
    }

    override fun handle(eventQueue: EventQueue, command: NotifyPayCommand) {
        if (!inPark()) {
            throw DomainException("车辆不在场，不能付费")
        }

        lastPlayTime = command.payTime
        totalPaid += command.amount

        eventQueue.enqueue(PaidEvent(plate = id, amount = command.amount, payTime = command.payTime))
    }

    override fun handle(eventQueue: EventQueue, command: CheckOutCommand): Boolean {
        if (!inPark()) {
            eventQueue.enqueue(CheckOutFailedEvent(plate = id, time = command.time, message = "车辆不在场"))
            return false
        }

        if (calculateFeeNow(command.time) > 0) {
            return false
        }

        this.checkInTime = null
        this.totalPaid = 0
        this.lastPlayTime= null

        eventQueue.enqueue(CheckedOutEvent(plate = id, time = command.time))
        return true
    }

    override fun calculateFeeNow(now: LocalDateTime): Int {
        val currentCheckInTime = checkInTime ?: throw DomainException("车辆尚未入场")
        val lastPayTimeCurrent = lastPlayTime ?: return hoursBetween(currentCheckInTime, now)
        if (lastPayTimeCurrent.plusMinutes(15).isAfter(now)) {
            return 0
        }

        return hoursBetween(currentCheckInTime, now) - totalPaid
    }

    fun hoursBetween(start: LocalDateTime, end: LocalDateTime): Int {
        val minutes = Duration.between(start, end).toMinutes()
        val hours = minutes / 60
        return (if (hours * 60 == minutes) hours else hours + 1).toInt()
    }

    fun inPark(): Boolean {
        return checkInTime != null
    }

}