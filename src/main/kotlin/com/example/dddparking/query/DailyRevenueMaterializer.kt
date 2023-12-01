package com.example.dddparking.query

import com.example.dddparking.db.DailyRevenueDao
import com.example.dddparking.domain.DomainEvent
import com.example.dddparking.domain.DomainEventListener
import com.example.dddparking.domain.PaidEvent
import org.springframework.stereotype.Component

@Component
class DailyRevenueMaterializer(
    private val dailyRevenueDao: DailyRevenueDao
) : DomainEventListener {
    override fun onEvent(event: DomainEvent) {
        if (event !is PaidEvent) {
            return
        }

        dailyRevenueDao.increaseRevenueByDate(event.payTime.toLocalDate(), event.amount)
    }

}