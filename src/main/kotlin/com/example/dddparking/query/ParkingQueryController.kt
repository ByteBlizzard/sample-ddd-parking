package com.example.dddparking.query

import com.example.dddparking.db.ParkingViewDao
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class ParkingQueryController(
    private val parkingViewDao: ParkingViewDao
) {

    @QueryMapping
    fun parkingHistory(@Argument("plate") plate: String): List<ParkingHistoryVO> {
        return this.parkingViewDao.findAll().map {
            ParkingHistoryVO(
                plate = it.plate,
                checkInTime = it.checkInTime.toString(),
                checkOutTime = it.checkOutTime?.toString() ?: "",
                payAmount = it.payAmount
            )
        }
    }
}

class ParkingHistoryVO (
    val plate: String,
    val checkInTime: String,
    val checkOutTime: String,
    val payAmount: Int
)