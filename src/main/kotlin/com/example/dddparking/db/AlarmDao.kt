package com.example.dddparking.db

import org.springframework.data.jpa.repository.JpaRepository

interface AlarmDao : JpaRepository<AlarmTable, Long> {
}