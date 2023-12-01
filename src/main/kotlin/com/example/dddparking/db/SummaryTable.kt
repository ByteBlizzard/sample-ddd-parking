package com.example.dddparking.db

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component

/**
 * 总停车数统计视图
 */
@Entity
class SummaryTable (
    @Id
    val id: Int,

    var totalInParking: Int
)

interface SummaryDao: JpaRepository<SummaryTable, Int> {
    @Query("update SummaryTable s set s.totalInParking = s.totalInParking + ?2 where s.id = ?1 ")
    @Modifying
    fun updateById(id: Int, increment: Int)
}

@Component
class InitTotalInParking(
    private val summaryDao: SummaryDao
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        summaryDao.save(SummaryTable(id = 1, totalInParking = 0))
        LOG.info("SummaryDao initialized!")
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(InitTotalInParking::class.java)
    }
}