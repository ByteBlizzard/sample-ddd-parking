package com.example.dddparking.db

import org.springframework.data.jpa.repository.JpaRepository

interface ParkingViewDao: JpaRepository<ParkingViewTable, Long> {
    fun findTopByPlateOrderByIdDesc(plate: String): ParkingViewTable
}