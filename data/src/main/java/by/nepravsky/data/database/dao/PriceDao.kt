package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.nepravsky.data.database.entity.Price
import by.nepravsky.domain.entity.request.Settings

@Dao
interface PriceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(prices: List<Price>): List<Long>

    @Query("SELECT ${Price.TYPE_ID} FROM ${Price.TABLE_NAME} WHERE " +
            "${Price.TYPE_ID} IN (:typeIds) " +
            "AND ${Price.UPDATE_TIME} < :beforeTime " +
            "AND ${Price.SYSTEM_ID} == :solarSystemId "
    )
    fun get(typeIds: List<Int>, beforeTime: Long, solarSystemId: Int): List<Int>
}