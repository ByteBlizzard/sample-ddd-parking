package com.example.dddparking.db

import org.springframework.data.jpa.repository.JpaRepository


interface ParkingDao : JpaRepository<ParkingTable, String> {

}