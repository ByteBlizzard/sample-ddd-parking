package com.example.dddparking.domain

import org.springframework.stereotype.Component

@Component
class AlarmPolicy(
    private val alarmService: AlarmService
) : DomainEventListener{
    override fun onEvent(event: DomainEvent) {
        if (event is CheckInFailedEvent) {
            alarmService.alarm(event.plate, "入场失败")
            return
        }

        if (event is CheckOutFailedEvent) {
            alarmService.alarm(event.plate, event.message)
        }
    }
}