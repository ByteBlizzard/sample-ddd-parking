package com.example.dddparking.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ParkingImplTest {
    @Test
    fun test_case0() {
        try {
            ParkingImpl(id = Plate("p1"), checkInTime = null, lastPlayTime = null).calculateFeeNow(LocalDateTime.now())
            fail()
        } catch (e: DomainException) {
            assertEquals("车辆尚未入场", e.message)
        }
    }

    @Test
    fun test_case1() {
        val result = ParkingImpl(
            id = Plate("p1"),
            checkInTime = LocalDateTime.of(2023, 11, 26, 10, 0, 0),
            lastPlayTime = null
        ).calculateFeeNow(LocalDateTime.of(2023, 11, 26, 10, 59, 59))
        assertEquals(1, result)
    }

    @Test
    fun test_case2() {
        val result = ParkingImpl(
            id = Plate("p1"),
            checkInTime = LocalDateTime.of(2023, 11, 26, 10, 0, 0),
            lastPlayTime = null
        ).calculateFeeNow(LocalDateTime.of(2023, 11, 26, 11, 59, 59))
        assertEquals(2, result)
    }

    @Test
    fun test_case3() {
        val result = ParkingImpl(
            id = Plate("p1"),
            checkInTime = LocalDateTime.of(2023, 11, 26, 10, 0, 0),
            lastPlayTime = LocalDateTime.of(2023, 11, 26, 10, 30, 0),
            totalPaid = 1
        ).calculateFeeNow(LocalDateTime.of(2023, 11, 26, 10, 44, 59))
        assertEquals(0, result)
    }

    @Test
    fun test_case4() {
        val result = ParkingImpl(
            id = Plate("p1"),
            checkInTime = LocalDateTime.of(2023, 11, 26, 10, 0, 0),
            lastPlayTime = LocalDateTime.of(2023, 11, 26, 10, 30, 0),
            totalPaid = 1
        ).calculateFeeNow(LocalDateTime.of(2023, 11, 26, 10, 46, 59))
        assertEquals(0, result)
    }

    @Test
    fun test_case5() {
        val result = ParkingImpl(
            id = Plate("p1"),
            checkInTime = LocalDateTime.of(2023, 11, 26, 10, 0, 0),
            lastPlayTime = LocalDateTime.of(2023, 11, 26, 10, 30, 0),
            totalPaid = 1
        ).calculateFeeNow(LocalDateTime.of(2023, 11, 26, 11, 31, 59))
        assertEquals(1, result)
    }

    @Test
    fun `复杂场景，付款超时后再付款`() {
        val plate = Plate("p1")
        //未入场
        val target = ParkingImpl(
            id = plate,
            checkInTime = null,
            lastPlayTime = null,
            totalPaid = 0
        )
        assertFalse(target.inPark())

        // 入场
        val eventQueue = TestEventQueue()
        target.handle(eventQueue, CheckInCommand(plate, LocalDateTime.of(2023, 11, 26, 10, 0, 0)))
        assertTrue(target.inPark())
        assertEquals(1, eventQueue.list.size)
        assertTrue(eventQueue.list[0] is CheckedInEvent)

        // 半小时后，查询费用 1 块
        assertEquals(1, target.calculateFeeNow(LocalDateTime.of(2023, 11, 26, 10, 30, 0)))

        // 付钱 1块
        val eventQueue1 = TestEventQueue()
        target.handle(eventQueue1, NotifyPayCommand(
            plate = plate,
            payTime = LocalDateTime.of(2023, 11, 26, 10, 30, 0),
            amount = 1
        ))

        // 超时15分钟，离场失败
        val eventQueue2 = TestEventQueue()
        assertFalse(target.handle(eventQueue2, CheckOutCommand(plate = plate, time = LocalDateTime.of(2023, 11, 26, 11, 1, 0) )))

        // 查询应付1块
        assertEquals(1, target.calculateFeeNow(LocalDateTime.of(2023, 11, 26, 11, 1, 0)))

        // 付款1块
        val eventQueue3 = TestEventQueue()
        target.handle(eventQueue3, NotifyPayCommand(
            plate = plate,
            payTime = LocalDateTime.of(2023, 11, 26, 11, 2, 0),
            amount = 1
        ))

        // 出场成功
        val eventQueue4 = TestEventQueue()
        assertTrue(target.handle(eventQueue4, CheckOutCommand(plate = plate, time = LocalDateTime.of(2023, 11, 26, 11, 3, 0) )))
    }

    @Test
    fun `车辆入场付款出场再进场`() {

        val plate = Plate("p1")
        //未入场
        val target = ParkingImpl(
            id = plate,
            checkInTime = null,
            lastPlayTime = null,
            totalPaid = 0
        )
        assertFalse(target.inPark())

        // 入场
        val eventQueue = TestEventQueue()
        target.handle(eventQueue, CheckInCommand(plate, LocalDateTime.of(2023, 11, 26, 10, 0, 0)))
        assertTrue(target.inPark())
        assertEquals(1, eventQueue.list.size)
        assertTrue(eventQueue.list[0] is CheckedInEvent)

        // 付钱 1块
        val eventQueue1 = TestEventQueue()
        target.handle(eventQueue1, NotifyPayCommand(
            plate = plate,
            payTime = LocalDateTime.of(2023, 11, 26, 10, 30, 0),
            amount = 1
        ))

        // 出场成功
        val eventQueue2 = TestEventQueue()
        assertTrue(target.handle(eventQueue2, CheckOutCommand(plate = plate, time = LocalDateTime.of(2023, 11, 26, 10, 31, 0) )))
        assertFalse(target.inPark())
        assertEquals(1, eventQueue2.list.size)
        assertTrue(eventQueue2.list[0] is CheckedOutEvent)

        // 再进场
        val eventQueue3 = TestEventQueue()
        target.handle(eventQueue3, CheckInCommand(plate, LocalDateTime.of(2023, 11, 26, 11, 0, 0)))
        assertTrue(target.inPark())
        assertEquals(1, eventQueue3.list.size)
        assertTrue(eventQueue3.list[0] is CheckedInEvent)
    }
}