package com.example.dddparking.query

import com.example.dddparking.db.SummaryDao
import com.example.dddparking.domain.CheckedInEvent
import com.example.dddparking.domain.CheckedOutEvent
import com.example.dddparking.domain.DomainEvent
import com.example.dddparking.domain.DomainEventListener
import org.springframework.stereotype.Component

@Component
class SummaryMaterializer(
    private val summaryDao: SummaryDao
) : DomainEventListener{
    override fun onEvent(event: DomainEvent) {
        when (event) {
            is CheckedInEvent -> summaryDao.updateById(1, 1)
            is CheckedOutEvent -> summaryDao.updateById(1, -1)
        }
    }
}