package com.example.dddparking.db

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.boot.CommandLineRunner
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import java.time.LocalDate

/**
 * 日收入汇总视图
 */
@Entity
class DailyRevenueTable (
    @Id
    val id: LocalDate,

    var revenue: Int
)

interface DailyRevenueDao: JpaRepository<DailyRevenueTable, LocalDate> {
    @Query("update DailyRevenueTable d set d.revenue = d.revenue + ?2 where d.id = ?1")
    @Modifying
    fun increaseRevenueByDate(id: LocalDate, increasement: Int)
}

@Component
class InitDailyRevenue(
    private val dailyRevenueDao: DailyRevenueDao
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        dailyRevenueDao.save(DailyRevenueTable(id = LocalDate.now(), revenue = 0))
    }

}