package com.example.dddparking.domain

interface ParkingRepository {
    fun findByIdOrError(plate: Plate): Parking
    fun save(parking: Parking)
}