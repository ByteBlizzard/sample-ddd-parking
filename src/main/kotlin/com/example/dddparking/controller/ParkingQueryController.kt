package com.example.dddparking.controller

import com.example.dddparking.db.DailyRevenueDao
import com.example.dddparking.db.ParkingViewDao
import com.example.dddparking.db.SummaryDao
import org.springframework.data.domain.Sort
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class ParkingQueryController(
    private val parkingViewDao: ParkingViewDao,
    private val summaryDao: SummaryDao,
    private val dailyRevenueDao: DailyRevenueDao
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

    @QueryMapping
    fun totalInPark(): Int {
        return summaryDao.findById(1).map { it.totalInParking }.orElse(0)
    }

    @QueryMapping
    fun dailyRevenue(): List<DailyRevenueVO> {
        return this.dailyRevenueDao.findAll(Sort.by("id").descending())
            .map {
                DailyRevenueVO(
                    date = it.id.toString(),
                    revenue = it.revenue
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

class DailyRevenueVO (
    val date: String,
    val revenue: Int
)