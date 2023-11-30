package com.example.dddparking.domain

import com.example.dddparking.db.ParkingDao
import com.example.dddparking.db.ParkingTable
import org.springframework.stereotype.Component

@Component
class ParkingRepositoryImpl(
    private val parkingDao: ParkingDao
): ParkingRepository {
    override fun findByIdOrError(plate: Plate): Parking {
        return parkingDao.findById(plate.value).map {
            ParkingImpl(
                id = plate,
                checkInTime = it.checkInTime,
                lastPlayTime = it.lastPlayTime,
                totalPaid = it.totalPaid
            )
        }.orElse(
            ParkingImpl(
                id = plate,
                checkInTime = null
            )
        )
    }

    override fun save(parking: Parking) {
        if (parking !is ParkingImpl) {
            throw UnsupportedOperationException("不支持的类型: ${parking.javaClass}")
        }
        parkingDao.save(ParkingTable(
            id = parking.id.value,
            checkInTime = parking.checkInTime,
            lastPlayTime = parking.lastPlayTime,
            totalPaid = parking.totalPaid
        ))
    }
}