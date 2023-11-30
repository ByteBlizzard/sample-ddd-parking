package com.example.dddparking.query

import com.example.dddparking.db.ParkingViewDao
import com.example.dddparking.db.ParkingViewTable
import com.example.dddparking.domain.*
import org.springframework.stereotype.Component

@Component
class ParkingHistoryMaterializer(
    private val parkingViewDao: ParkingViewDao
): DomainEventListener {
    override fun onEvent(event: DomainEvent) {
        when (event) {
            is CheckedInEvent -> insertHistory(event)
            is CheckedOutEvent -> updateOnCheckOut(event)
            is PaidEvent -> updateOnPaid(event)
        }
    }

    private fun updateOnPaid(event: PaidEvent) {
        val parkingViewRecord = parkingViewDao.findTopByPlateOrderByIdDesc(event.plate.value)
        parkingViewRecord.payAmount += event.amount
        parkingViewDao.save(parkingViewRecord)
    }

    private fun updateOnCheckOut(event: CheckedOutEvent) {
        val parkingViewRecord = parkingViewDao.findTopByPlateOrderByIdDesc(event.plate.value)
        parkingViewRecord.checkOutTime = event.time
        parkingViewDao.save(parkingViewRecord)
    }

    private fun insertHistory(event: CheckedInEvent) {
        parkingViewDao.save(ParkingViewTable(
            id = null,
            plate = event.plate.value,
            checkInTime = event.time,
            checkOutTime = null,
            payAmount = 0
        ))
    }
}