package com.example.dddparking.domain

import com.example.dddparking.db.AlarmDao
import com.example.dddparking.db.AlarmTable
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * 以往数据库记录数据的方式实现报警
 */
@Component
class AlarmServiceDBImpl(
    private val alarmDao: AlarmDao
) : AlarmService {
    override fun alarm(plate: Plate, message: String) {
        alarmDao.save(AlarmTable(
            id = null,
            plate = plate.value,
            msg = message,
            time = LocalDateTime.now()
        ))
    }
}