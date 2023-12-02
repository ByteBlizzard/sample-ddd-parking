package com.example.dddparking.db

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * 停车聚合的持久化信息
 */
@Entity
@Table(name = "parking")
class ParkingTable (
    /**
     * 车牌号
     */
    @Id
    val id: String,

    var checkInTime: LocalDateTime?,

    var lastPlayTime: LocalDateTime?,

    var totalPaid: Int
)