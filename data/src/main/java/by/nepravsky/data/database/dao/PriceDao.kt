package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.nepravsky.data.database.entity.Price

@Dao
interface PriceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(prices: List<Price>): List<Long>

    @Query("SELECT ${Price.TYPE_ID} FROM ${Price.TABLE_NAME} WHERE " +
            "${Price.TYPE_ID} IN (:typeIds) AND ${Price.UPDATE_TIME} < :beforeTime"
    )
    fun get(typeIds: List<Int>, beforeTime: Long): List<Int>
}