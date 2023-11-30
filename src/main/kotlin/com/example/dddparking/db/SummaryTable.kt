package com.example.dddparking.db

import jakarta.persistence.Entity
import jakarta.persistence.Id

/**
 * 总停车数统计视图
 */
@Entity
class SummaryTable (
    @Id
    val id: Int,

    var totalInParking: Int
)