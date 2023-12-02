package com.example.dddparking.db

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * 停车视图
 */
@Entity
@Table(name = "parking_view")
class ParkingViewTable (
    @Id
    @GeneratedValue
    var id: Long?,

    val plate: String,

    var checkInTime: LocalDateTime?,

    var checkOutTime: LocalDateTime?,

    var payAmount: Int
)