package com.example.dddparking.db

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDate

/**
 * 日收入汇总视图
 */
@Entity
class DailyRevenueTable (
    @Id
    val date: LocalDate,

    var revenue: Int
)